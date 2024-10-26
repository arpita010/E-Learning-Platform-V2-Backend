package com.app.filestore;

import com.app.commons.SuperEntity;
import com.app.enums.FileType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FileStore extends SuperEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "mediumtext")
  private String fileName;

  @Column(columnDefinition = "longtext")
  private String githubDownloadUrl; // download url for frontend

  @Column(columnDefinition = "longtext")
  @JsonIgnore
  private String githubApiResponse; // will be Base64-Encoded

  private String repoName;

  @Enumerated(EnumType.STRING)
  private FileType fileType;

  private Long referenceId;
}
