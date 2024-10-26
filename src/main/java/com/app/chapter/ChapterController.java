package com.app.chapter;

import com.app.chapter.request.ChapterRequest;
import com.app.chapter.response.ChapterListResponse;
import com.app.chapter.response.ChapterResponse;
import com.app.commons.SuperResponse;
import com.app.course.Course;
import com.app.course.CourseService;
import com.app.enums.FileType;
import com.app.enums.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/chapter")
@RequiredArgsConstructor
public class ChapterController {
  private final ChapterService chapterService;
  private final CourseService courseService;

  @PostMapping("/course/{courseId}/create")
  public ChapterResponse create(
      @RequestBody ChapterRequest request, @PathVariable("courseId") Long courseId)
      throws Exception {
    Course course = courseService.getById(courseId);
    return chapterService.create(request, course);
  }

  @PostMapping("/{chapterId}/update")
  public ChapterResponse update(
      @PathVariable("chapterId") Long chapterId, @RequestBody ChapterRequest request)
      throws Exception {
    return chapterService.update(chapterId, request);
  }

  @PostMapping("/{chapterId}/image/upload")
  public ChapterResponse uploadThumbnailImage(
      @PathVariable("chapterId") Long chapterId,
      @RequestParam("file") MultipartFile file,
      @RequestParam("fileName") String fileName,
      @RequestParam("fileType") FileType fileType)
      throws Exception {
    String exactFileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
    return chapterService.uploadThumbnailImage(chapterId, file, exactFileName, fileType);
  }

  @PostMapping("/{chapterId}/video/upload")
  public ChapterResponse uploadChapterVideo(
      @PathVariable Long chapterId,
      @RequestParam MultipartFile file,
      @RequestParam String fileName,
      @RequestParam FileType fileType)
      throws Exception {
    String exactFileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
    return chapterService.uploadChapterVideo(chapterId, file, exactFileName, fileType);
  }

  @GetMapping("/{chapterId}/fetch")
  public ChapterResponse fetchByID(@PathVariable Long chapterId) throws Exception {
    return chapterService.findById(chapterId);
  }

  @GetMapping("/course/{courseId}/fetchAll")
  public ChapterListResponse fetchAllByCourse(
      @PathVariable Long courseId,
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "") String queryName)
      throws Exception {
    Course course = courseService.getById(courseId);
    return chapterService.fetchAllByCourse(pageNo, pageSize, course, queryName);
  }

  @PostMapping("/{chapterId}/delete")
  public SuperResponse deleteById(@PathVariable Long chapterId) throws Exception {
    chapterService.deleteById(chapterId);
    return new SuperResponse(ResponseStatus.SUCCESS);
  }
}
