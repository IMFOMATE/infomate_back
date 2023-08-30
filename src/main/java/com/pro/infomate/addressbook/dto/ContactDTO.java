package com.pro.infomate.addressbook.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactDTO {

    private Integer contactCode;

    private MultipartFile contactPhoto;

    private String company;

    private String department;

    private String contactName;

    private String contactPhone;

    private String contactEmail;

    private String companyPhone;

    private String companyAddress;

    private String memo;

    private Integer memberCode;

    private char contactLike;



}
