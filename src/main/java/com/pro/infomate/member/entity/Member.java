package com.pro.infomate.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.pro.infomate.calendar.entity.Participant;
import com.pro.infomate.work.entity.Off;
import com.pro.infomate.work.entity.Work;
import com.pro.infomate.addressbook.entity.Contact;
import com.pro.infomate.approval.entity.Approval;
import com.pro.infomate.approval.entity.DocRef;
import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.department.entity.Department;
import com.pro.infomate.email.entity.Email;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Timestamp;

@Entity
@Table(name = "TBL_MEMBER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@ToString
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
    private int memberCode;

    @Column(name = "MEMBER_PWD")
    private String memberPassword;

    @Column(name = "MEMBER_NAME")
    private String memberName;

    @Column(name = "MEMBER_ID")
    private String memberId;

    @Column(name = "MEMBER_EMAIL")
    private String memberEmail;

    @Column(name = "MEMBER_PHONE")
    private String memberPhone;

    @Column(name = "MEMBER_NO")
    private String memberNo;

    @Column(name = "MEMBER_STATUS")
    private String memberStatus;

    @Column(name = "EXTENSION_NUMBER")
    private String extensionNumber;

    @OneToMany(mappedBy = "member")
    private List<Contact> contactList;

    @OneToMany(mappedBy = "member")
    private List<Email> emailList;

    @Column(name = "MEMBER_ADDRESS")
    private String memberAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPT_CODE", insertable = false,updatable = false)
    private Department department;

    @Column(name = "HIRE_DATE")
    private Timestamp hireDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RANK_CODE", insertable = false,updatable = false)
    private Rank rank;

    @Column(name = "RANK_CODE")
    private int rankCode;

    @Column(name = "MEMBER_PIC")
    private String memberPic;


    @Column(name = "MEMBER_OFF")
    private int memberOff;

    @Column(name = "DEPT_CODE")
    private int deptCode;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "MEMBER_CODE")
    private List<AuthList> authList;

    @OneToMany(mappedBy = "member")
    private List<DocRef> memberRefList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Approval> approvalList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Document> documentList = new ArrayList<>();



    @OneToMany(mappedBy = "member")
    private List<Work> workList;

    @OneToMany(mappedBy = "member")
    private List<Off> OffList;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Participant> participantList;

    @Override
    public String toString() {
        return "Member{" +
                "memberCode=" + memberCode +
                ", memberPassword='" + memberPassword + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberId='" + memberId + '\'' +
                ", memberEmail='" + memberEmail + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                ", memberNo='" + memberNo + '\'' +
                ", memberStatus='" + memberStatus + '\'' +
                ", extensionNumber='" + extensionNumber + '\'' +
                ", memberAddress='" + memberAddress + '\'' +
                ", department=" + department +
                ", hireDate=" + hireDate +
                ", rank=" + rank +
                ", memberPic='" + memberPic + '\'' +
                ", memberOff=" + memberOff +
                ", authList=" + authList +
                '}';
    }
}

