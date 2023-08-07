package com.pro.infomate.addressbook.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_CONTACT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(
        name = "SEQ_TBL_CONTACT_GENERATOR",
        sequenceName = "SEQ_TBL_CONTACT_CODE",
        initialValue = 1, allocationSize = 1
)
public class Contact {

    @Id
    @Column(name = "CONTACT_CODE")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_TBL_CONTACT_GENERATOR"
    )
    private int contactCode;

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "DEPARTMENT")
    private String department;

    @Column(name = "CONTACT_NAME")
    private String contactName;

    @Column(name = "CONTACT_PHONE")
    private String contactPhone;

    @Column(name = "CONTACT_EMAIL")
    private String contactEmail;

    @Column(name = "COMPANY_PHONE")
    private String companyPhone;

    @Column(name = "COMPANY_ADDRESS")
    private String companyAddress;

    @Column(name = "MEMO")
    private String memo;

    @OneToOne
    @JoinColumn(name = "MEMBER_CODE", nullable = false)
    private Member member;

}
