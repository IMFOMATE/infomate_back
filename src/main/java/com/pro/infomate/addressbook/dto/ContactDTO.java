package com.pro.infomate.addressbook.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactDTO {

    private int contactCode;

    private String company;

    private String department;

    private String contactName;

    private String contactPhone;

    private String contactEmail;

    private String companyPhone;

    private String companyAddress;

    private String memo;

    private int memberCode;

}
