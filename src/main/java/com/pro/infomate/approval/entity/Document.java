package com.pro.infomate.approval.entity;

import com.pro.infomate.approval.dto.response.DocumentDetailResponse;
import com.pro.infomate.approval.service.visitor.DocumentVisitor;
import com.pro.infomate.member.entity.Member;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TBL_DOCUMENT")
@Inheritance(strategy = InheritanceType.JOINED)
@DynamicInsert
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

  @Column(name = "DOCUMENT_DATE")
  private LocalDateTime createdDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "DOCUMENT_STATUS")
  private DocumentStatus documentStatus;

  @Column(name = "DOCUMENT_CONTENT")
  @Lob
  private String content;

  @Column(name = "EMERGENCY")
  private String emergency;

  @Column(name = "DOCUMENT_KIND", insertable = false, updatable = false)
  private String documentKind;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MEMBER_CODE")
  private Member member;

  @OneToMany(mappedBy = "document", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<DocumentFile> fileList = new ArrayList<>();

  @OneToMany(mappedBy = "document", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<Approval> approvalList = new ArrayList<>();

  @OneToMany(mappedBy = "document", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<DocRef> refList= new ArrayList<>();


  //편의 메소드
  public void addFile(DocumentFile file){
    fileList.add(file);
    if(file.getDocument() != this){
      file.setDocument(this);
    }
  }

  public void addApproval(Approval approval){
    approvalList.add(approval);
    if(approval.getDocument() != this ){
      approval.setDocument(this);
    }
  }

  public void addRef(DocRef docRef){
    refList.add(docRef);
    if(docRef.getDocument() != this ){
      docRef.setDocument(this);
    }
  }

  public void addMember(Member member){
    if(this.member != null){
      this.member.getDocumentList().remove(this);
    }

    this.member = member;

    if(!member.getDocumentList().contains(this)) {
      member.getDocumentList().add(this);
    }
  }

  public abstract DocumentDetailResponse accept(DocumentVisitor<DocumentDetailResponse> visitor);


}

