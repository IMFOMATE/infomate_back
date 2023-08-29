package com.pro.infomate.member.entity;

import com.pro.infomate.addressbook.entity.Contact;
import com.pro.infomate.approval.entity.Approval;
import com.pro.infomate.approval.entity.DocMemberRef;
import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.email.entity.Email;
import lombok.*;

import javax.persistence.*;
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

  //나머지는 만들어주세요 FK도 연결하셔야해요

  @OneToMany(mappedBy = "member")
  private List<Contact> contactList;

  @OneToMany(mappedBy = "member")
  private List<Email> emailList;




  @OneToMany(mappedBy = "member")
  private List<DocMemberRef> memberRefList;

  @OneToMany(mappedBy = "member")
  private List<Approval> approvalList;

  @OneToMany(mappedBy = "member")
  private List<Document> documentList;



}
