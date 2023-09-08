package com.pro.infomate.email.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PhotoFileDTO {

    private Integer fileCode;

    private Integer mailCode;

    private Date fileDate;

    private String fileModificationName;
}
