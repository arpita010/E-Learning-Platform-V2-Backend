package com.app.filestore;

import com.app.enums.FileType;
import com.app.githubClient.GithubClient;
import com.app.githubClient.response.GithubFileUploadResponse;
import com.app.utils.Encrypter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStoreService {
  private final FileStoreRepo fileStoreRepo;
  private final GithubClient githubClient;
  private ObjectMapper objectMapper = new ObjectMapper();
  private static Map<FileType, String> repoNameMap = new HashMap<>();

  private static final String imagesDbRepoName = "E-Learning-Images-DB";

  private static final String videosDbRepoName = "E-Learning-Video-DB";

  static {
    repoNameMap.put(FileType.CLASS_IMAGE, imagesDbRepoName);
    repoNameMap.put(FileType.COURSE_IMAGE, imagesDbRepoName);
    repoNameMap.put(FileType.CHAPTER_IMAGE, imagesDbRepoName);
    repoNameMap.put(FileType.CHAPTER_VIDEO, videosDbRepoName);
  }

  public FileStore uploadFileToServer(
      MultipartFile file, String fileName, FileType fileType, Long referenceId) throws Exception {
    FileStore fileStoreResponse = null;
    try {
      String randomString = String.valueOf(UUID.randomUUID());
      String updatedFileName = randomString + "_" + fileName;
      String repoName = repoNameMap.get(fileType);
      //      log.info("Repo name : {}", repoName);
      String githubResponse = githubClient.uploadFile(file, updatedFileName, repoName);
      GithubFileUploadResponse response =
          objectMapper.readValue(githubResponse, GithubFileUploadResponse.class);
      String downloadUrl =
          response != null && response.getContent() != null
              ? response.getContent().getDownloadUrl()
              : null;
      FileStore fileStore = new FileStore();
      fileStore.setFileName(updatedFileName);
      fileStore.setFileType(fileType);
      fileStore.setGithubDownloadUrl(downloadUrl);
      fileStore.setGithubApiResponse(Encrypter.encrypt(githubResponse));
      fileStore.setRepoName(repoNameMap.get(fileType));
      fileStore.setReferenceId(referenceId);
      fileStoreResponse = fileStoreRepo.save(fileStore);
    } catch (Exception e) {
      log.error(
          "Error while uploading file to server inside file storage service :  {}", e.getMessage());
    }
    return fileStoreResponse;
  }
}
