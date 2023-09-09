package com.pro.infomate.approval.entity;

import com.pro.infomate.approval.dto.response.DocFileResponse;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TBL_DOCFILE")
@DynamicInsert
@SequenceGenerator(
        name = "FILE_CODE_GENERATOR",
        sequenceName = "SEQ_TBL_DOCFILE_FILE_CODE",
        allocationSize = 1,
        initialValue = 1
)
public class DocumentFile {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILE_CODE_GENERATOR")
  @Column(name = "FILE_CODE")
  private Long fileCode;

  @Column(name = "FILE_NAME")
  private String fileName;

  @Column(name = "FILE_ORGIN_NAME")
  private String originalName;

  @Column(name = "FILE_TYPE")
  private String fileType;

  @Column(name = "FILE_SIZE")
  private long fileSize;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DOCUMENT_ID")
  private Document document;

  public DocumentFile(DocFileResponse fileResponse, Document document){
    this.fileName = fileResponse.getFileName();
    this.originalName = fileResponse.getOriginalName();
    this.fileSize = fileResponse.getFileSize();
    this.fileType = fileResponse.getFileType();
    this.document = document;
  }

  @Builder
  public DocumentFile(String fileName, String originalName, String fileType, long fileSize, Document document) {
    this.fileName = fileName;
    this.originalName = originalName;
    this.fileType = fileType;
    this.fileSize = fileSize;
    this.document = document;
  }
}