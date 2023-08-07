package com.pro.infomate.email.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PhotoFileDTO {

    private int fileCode;

    private int mailCode;

    private String fileDate;

    private String fileSourceName;

    private String fileModificationName;
}
