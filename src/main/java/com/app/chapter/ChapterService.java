package com.app.chapter;

import com.app.chapter.request.ChapterRequest;
import com.app.chapter.response.ChapterListResponse;
import com.app.chapter.response.ChapterResponse;
import com.app.course.Course;
import com.app.enums.FileType;
import com.app.exceptions.ChapterException;
import com.app.filestore.FileStore;
import com.app.filestore.FileStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChapterService {
  private final ChapterRepo chapterRepo;
  private final FileStoreService fileStoreService;

  public ChapterResponse create(ChapterRequest request, Course course) throws Exception {
    Chapter chapter = request.toChapter();
    chapter.setCourse(course);
    Chapter saved = chapterRepo.save(chapter);
    return new ChapterResponse(saved);
  }

  public ChapterResponse update(Long chapterId, ChapterRequest request) throws Exception {
    Optional<Chapter> opt = chapterRepo.findById(chapterId);
    if (opt.isEmpty()) throw new ChapterException("Invalid Chapter ID");
    Chapter chapter = opt.get();
    request.updateDetails(chapter);
    Chapter saved = chapterRepo.save(chapter);
    return new ChapterResponse(saved);
  }

  public ChapterResponse uploadThumbnailImage(
      Long chapterId, MultipartFile file, String fileName, FileType fileType) throws Exception {
    log.info("Thumbnail Image File Name : {}", fileName);
    Optional<Chapter> opt = chapterRepo.findById(chapterId);
    if (opt.isEmpty()) throw new ChapterException("Invalid Chapter ID");
    Chapter chapter = opt.get();
    FileStore fileStore = fileStoreService.uploadFileToServer(file, fileName, fileType, chapterId);
    chapter.setThumbnailUrl(fileStore.getGithubDownloadUrl());
    Chapter saved = chapterRepo.save(chapter);
    return new ChapterResponse(saved);
  }

  public ChapterResponse uploadChapterVideo(
      Long chapterId, MultipartFile file, String fileName, FileType fileType) throws Exception {
    log.info("Video File Name : {}", fileName);
    Optional<Chapter> opt = chapterRepo.findById(chapterId);
    if (opt.isEmpty()) throw new ChapterException("Invalid Chapter ID");
    Chapter chapter = opt.get();
    FileStore fileStore = fileStoreService.uploadFileToServer(file, fileName, fileType, chapterId);
    chapter.setVideoUrl(fileStore.getGithubDownloadUrl());
    Chapter saved = chapterRepo.save(chapter);
    return new ChapterResponse(saved);
  }

  public ChapterResponse findById(Long chapterId) throws Exception {
    Optional<Chapter> opt = chapterRepo.findById(chapterId);
    if (opt.isEmpty()) throw new ChapterException("Invalid Chapter ID");
    return new ChapterResponse(opt.get());
  }

  public ChapterListResponse fetchAllByCourse(
      Integer pageNo, Integer pageSize, Course course, String queryName) throws Exception {
    Pageable paging = PageRequest.of(pageNo, pageSize);
    String queryInput = "%" + queryName + "%";
    Page<Chapter> chapterList =
        chapterRepo.findAllByCourseAndNameLikeIgnoreCaseOrderByCreatedAtDesc(
            course, queryInput, paging);
    List<ChapterResponse> chapters = new ArrayList<>();
    if (!chapterList.isEmpty() && !chapterList.getContent().isEmpty()) {
      for (Chapter chapter : chapterList.getContent()) {
        chapters.add(new ChapterResponse(chapter));
      }
    }
    return ChapterListResponse.builder()
        .pageNo(chapterList.getNumber())
        .pageSize(chapterList.getSize())
        .totalPages(chapterList.getTotalPages())
        .totalRecords(chapterList.getTotalElements())
        .isLastPage(chapterList.isLast())
        .chapters(chapters)
        .build();
  }

  public void deleteById(Long chapterId) throws Exception {
    Optional<Chapter> opt = chapterRepo.findById(chapterId);
    if (opt.isEmpty()) throw new ChapterException("Invalid Chapter ID");
    chapterRepo.delete(opt.get());
  }

  public Long getTotalCountOfChapters(Long courseId) throws Exception {
    Long count = chapterRepo.countByCourseId(courseId);
    return count;
  }
}
