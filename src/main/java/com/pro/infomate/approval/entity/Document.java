package com.pro.infomate.approval.entity;

import com.pro.infomate.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TBL_DOCUMENT")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DOCUMENT_KIND", discriminatorType = DiscriminatorType.STRING)
@SequenceGenerator(
        name = "DOCUMENT_ID_GENERATOR",
        sequenceName = "SEQ_TBL_DOCUMENT_DOCUMENT_ID",
        initialValue = 1,
        allocationSize = 1
)
public abstract class Document {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCUMENT_ID_GENERATOR")
  @Column(name = "DOCUMENT_ID")
  private Long id;

  @Column(name = "DOCUMENT_TITLE", nullable = false)
  private String title;

  @Column(name = "DOCUMENT_DATE", nullable = false)
  private LocalDateTime createdDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "DOCUMENT_STATUS")
  private DocumentStatus documentStatus;

  @Column(name = "DOCUMENT_CONTENT")
  @Lob
  private String content;

  @Column(name = "CO_DEPT")
  private String coDept;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MEMBER_CODE")
  private Member member;

  @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
  List<DocumentFile> fileList;

  @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Approval> approvalList;


  //편의 메소드
  public void addFile(DocumentFile file){
    fileList.add(file);
    file.setDocument(this);
  }

  public void addApproval(Approval approval){
    approvalList.add(approval);
    approval.setDocument(this);
  }

  public void AddMember(Member member){
    if(this.member != null){
      this.member.getDocumentList().remove(this);
    }

    this.member = member;
    member.getDocumentList().add(this);
  }


}
