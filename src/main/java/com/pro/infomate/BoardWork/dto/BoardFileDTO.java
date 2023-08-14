package com.pro.infomate.BoardWork.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardFileDTO {

    private int fileNo; // 첨부 파일 번호

    private int postCode;

    private String fileName;    // 변경된 파일명도 필요한데

    private String fileOriginal;    // 원본 파일명도 필요하다고 함
}
