package com.pro.infomate.member.entity;

import com.pro.infomate.approval.entity.Approval;
import com.pro.infomate.approval.entity.DocRef;
import com.pro.infomate.approval.entity.Document;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TBL_MEMBER")
@SequenceGenerator(
        name = "MEMBER_CODE_GENERATOR",
        sequenceName = "SEQ_TBL_MEMBER_MEMBER_CODE",
        initialValue = 1,
        allocationSize = 1
)
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_CODE_GENERATOR")
  @Column(name = "MEMBER_CODE")
  private Long memberCode;

  @Column(name = "MEMBER_ID")
  private String memberId;

  @Column(name = "MEMBER_NAME")
  private String memberName;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DEPT_CODE")
  private Department department;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "RANK_CODE")
  private Rank rank;




  @Column(name = "MEMBER_OFF")
  private int memberOff;

  @OneToMany(mappedBy = "member")
  private List<DocRef> memberRefList = new ArrayList<>();

  @OneToMany(mappedBy = "member")
  private List<Approval> approvalList = new ArrayList<>();

  @OneToMany(mappedBy = "member")
  private List<Document> documentList = new ArrayList<>();



}
