package com.pro.infomate.approval.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TBL_DOCFILE")
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DOCUMENT_ID")
  private Document document;


}