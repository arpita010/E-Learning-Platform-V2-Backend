package com.app.assignment;

import com.app.announcement.AnnouncementService;
import com.app.announcement.enums.AnnouncementStatus;
import com.app.announcement.enums.AnnouncementType;
import com.app.announcement.request.AnnouncementRequest;
import com.app.answerOption.AnswerOption;
import com.app.answerOption.AnswerOptionService;
import com.app.answerOption.request.AnswerOptionRequest;
import com.app.answerOption.response.AnswerOptionResponse;
import com.app.assignment.enums.AssignmentType;
import com.app.assignment.request.AssignmentRequest;
import com.app.assignment.response.AssignmentListResponse;
import com.app.assignment.response.AssignmentResponse;
import com.app.assignment.response.AssignmentTopicResponse;
import com.app.assignmentRecord.AssignmentRecord;
import com.app.assignmentRecord.AssignmentRecordService;
import com.app.assignmentRecord.enums.AssignmentRecordStatus;
import com.app.classroom.Classroom;
import com.app.classroom.ClassroomService;
import com.app.question.AssignmentQuestion;
import com.app.question.AssignmentQuestionService;
import com.app.question.enums.QuestionType;
import com.app.question.request.AssignmentQuestionRequest;
import com.app.question.response.AssignmentQuestionResponse;
import com.app.topic.Topic;
import com.app.topic.TopicService;
import com.app.topic.response.TopicResponse;
import com.app.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AssignmentService {
  private final AssignmentRepo page;
  private final ClassroomService classroomService;
  private final TopicService topicService;
  private final AssignmentQuestionService assignmentQuestionService;
  private final AnswerOptionService answerOptionService;
  private final AssignmentRecordService assignmentRecordService;
  private final AnnouncementService announcementService;

  public AssignmentResponse create(AssignmentRequest request) throws Exception {
    Classroom classroom = classroomService.getById(request.getClassroomId());
    if (classroom == null) throw new Exception("Invalid Request");
    Topic topic = request.getTopicId() != null ? topicService.getById(request.getTopicId()) : null;
    Assignment assignment = request.toAssignment(topic);
    assignment.setCreatorEmail(MDC.get("email"));
    Assignment saved = page.save(assignment);
    createQuestions(saved, request);
    createAssignmentRecord(saved, request);
    createAnnouncement(saved, request);
    AssignmentResponse response = new AssignmentResponse(saved);
    if (!AssignmentType.MATERIAL.equals(assignment.getType())
        && !AssignmentType.ASSIGNMENT.equals(assignment.getType())) {
      response.setQuestions(getAllQuestions(saved.getId()));
    }
    return response;
  }

  private void createAnnouncement(Assignment assignment, AssignmentRequest request)
      throws Exception {
    List<String> peoples = request.getAssignees();
    AnnouncementRequest announcementRequest =
        AnnouncementRequest.builder()
            .announcement("Assignment Posted")
            .classroomId(assignment.getClassroomId())
            .type(AnnouncementType.ASSIGNMENT)
            .status(
                null != assignment.getStatus()
                    ? AnnouncementStatus.valueOf(String.valueOf(assignment.getStatus()))
                    : null)
            .peoples(peoples)
            .build();
    announcementService.create(announcementRequest, assignment.getCreatorEmail());
  }

  private void createAssignmentRecord(Assignment assignment, AssignmentRequest request)
      throws Exception {
    List<String> emailList = request.getAssignees();
    for (String email : emailList) {
      AssignmentRecord record = new AssignmentRecord();
      record.setAssignmentId(assignment.getId());
      record.setUserEmail(email);
      record.setStatus(AssignmentRecordStatus.ASSIGNED);
      assignmentRecordService.save(record);
    }
  }

  private void createQuestions(Assignment assignment, AssignmentRequest request) throws Exception {
    if (AssignmentType.MATERIAL.equals(assignment.getType())
        || AssignmentType.ASSIGNMENT.equals(assignment.getType())) return;
    if (null == request.getQuestions()) return;
    List<AssignmentQuestionRequest> questionsList = request.getQuestions();
    for (AssignmentQuestionRequest question : questionsList) {
      AssignmentQuestion question1 = question.toAssignmentQuestion();
      question1.setAssignmentId(assignment.getId());
      AssignmentQuestion savedQues = assignmentQuestionService.save(question1);
      createAnswerOptions(savedQues, question);
    }
  }

  private void createAnswerOptions(AssignmentQuestion question, AssignmentQuestionRequest request) {
    if (QuestionType.SHORT_ANSWER.equals(question.getType())) return;
    List<AnswerOptionRequest> answerOptionRequests = request.getOptions();
    for (AnswerOptionRequest answerOptionRequest : answerOptionRequests) {
      AnswerOption option = answerOptionRequest.toAnswerOption();
      option.setQuestionId(question.getId());
      answerOptionService.save(option);
    }
  }

  public List<AnswerOptionResponse> getAnswerOptions(Long questionId) throws Exception {
    List<AnswerOption> options = answerOptionService.findAllByQuestion(questionId);
    return options.stream().map(AnswerOptionResponse::new).toList();
  }

  public List<AssignmentQuestionResponse> getAllQuestions(Long assignmentId) throws Exception {
    List<AssignmentQuestion> questionList =
        assignmentQuestionService.findAllByAssignment(assignmentId);
    List<AssignmentQuestionResponse> responses = new ArrayList<>();
    for (AssignmentQuestion question : questionList) {
      AssignmentQuestionResponse response = new AssignmentQuestionResponse(question);
      if (QuestionType.MULTIPLE_CHOICE.equals(question.getType())) {
        response.setOptions(getAnswerOptions(question.getId()));
      }
      responses.add(response);
    }
    return responses;
  }

  public AssignmentListResponse fetchAllByClassroomId(
      Long classroomId, Integer pageNo, Integer pageSize) throws Exception {
    Pageable paging = PageRequest.of(pageNo, pageSize);
    // it will throw exception if classroom id is invalid
    Classroom classroom = classroomService.getById(classroomId);
    Page<Assignment> assignmentPage =
        page.findAllByClassroomIdOrderByCreatedAtDesc(classroomId, paging);
    List<AssignmentTopicResponse> assignmentResponseList = new ArrayList<>();
    Map<Topic, List<AssignmentResponse>> groupedData = new HashMap<>();
    if (assignmentPage.hasContent()) {
      for (Assignment assignment : assignmentPage.getContent()) {
        List<AssignmentResponse> list =
            groupedData.getOrDefault(assignment.getTopic(), new ArrayList<>());
        AssignmentResponse assignmentResponse = new AssignmentResponse(assignment);
        assignmentResponse.setQuestions(getAllQuestions(assignment.getId()));
        list.add(assignmentResponse);
        groupedData.put(assignment.getTopic(), list);
      }
      for (Topic key : groupedData.keySet()) {
        AssignmentTopicResponse response = new AssignmentTopicResponse();
        response.setTopic(null == key ? null : new TopicResponse(key));
        response.setAssignmentsList(groupedData.get(key));
        assignmentResponseList.add(response);
      }
      assignmentResponseList.sort(
          (a, b) -> {
            if (a.getTopic() == null) return -1;
            if (b.getTopic() == null) return 1;
            return a.getTopic().getName().compareTo(b.getTopic().getName());
          });
    }
    return AssignmentListResponse.builder()
        .pageNo(assignmentPage.getNumber())
        .pageSize(assignmentPage.getSize())
        .totalPages(assignmentPage.getTotalPages())
        .totalRecords(assignmentPage.getTotalElements())
        .isLast(assignmentPage.isLast())
        .assignmentTopicResponses(assignmentResponseList)
        .build();
  }

  public Assignment getById(Long assignmentId) {
    Optional<Assignment> opt = page.findById(assignmentId);
    return opt.orElse(null);
  }

  public AssignmentListResponse fetchAllByUser(String email, Integer pageNo, Integer pageSize)
      throws Exception {
    Pageable paging = PageRequest.of(pageNo, pageSize);
    Page<AssignmentRecord> page = assignmentRecordService.fetchAllByUser(email, pageNo, pageSize);
    List<AssignmentTopicResponse> assignmentResponseList = new ArrayList<>();
    Map<Topic, List<AssignmentResponse>> groupedData = new HashMap<>();
    if (page.hasContent()) {
      for (AssignmentRecord record : page.getContent()) {
        Assignment assignment = getById(record.getAssignmentId());
        List<AssignmentResponse> list =
            groupedData.getOrDefault(assignment.getTopic(), new ArrayList<>());
        AssignmentResponse assignmentResponse = new AssignmentResponse(assignment);
        assignmentResponse.setQuestions(getAllQuestions(assignment.getId()));
        list.add(assignmentResponse);
        groupedData.put(assignment.getTopic(), list);
      }
      for (Topic key : groupedData.keySet()) {
        AssignmentTopicResponse response = new AssignmentTopicResponse();
        response.setTopic(null == key ? null : new TopicResponse(key));
        response.setAssignmentsList(groupedData.get(key));
        assignmentResponseList.add(response);
      }
      assignmentResponseList.sort(
          (a, b) -> {
            if (a.getTopic() == null) return -1;
            if (b.getTopic() == null) return 1;
            return a.getTopic().getName().compareTo(b.getTopic().getName());
          });
    }
    return AssignmentListResponse.builder()
        .pageNo(page.getNumber())
        .pageSize(page.getSize())
        .totalPages(page.getTotalPages())
        .totalRecords(page.getTotalElements())
        .isLast(page.isLast())
        .assignmentTopicResponses(assignmentResponseList)
        .build();
  }
}
