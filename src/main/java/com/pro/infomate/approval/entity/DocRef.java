package com.pro.infomate.approval.entity;

import com.pro.infomate.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TBL_DOCREF")
@SequenceGenerator(
        name = "REF_CODE_GENERATOR",
        sequenceName = "SEQ_TBL_DOCREF_REF_CODE",
        initialValue = 1,
        allocationSize = 1
)
public class DocRef {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REF_CODE_GENERATOR")
  @Column(name = "REF_CODE")
  private Long refCode;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DOCUMENT_ID")
  private Document document;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MEMBER_CODE")
  private Member member;

  @Builder
  public DocRef(Document document, Member member) {
    this.document = document;
    this.member = member;
  }
}
