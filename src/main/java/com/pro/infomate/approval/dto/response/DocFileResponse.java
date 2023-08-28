package com.pro.infomate.approval.dto.response;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
public class DocFileResponse {

    private Long fileCode; // 파일 코드

    @NotBlank
    private String fileName; // 파일명


    @Builder
    public DocFileResponse(Long fileCode, String fileName) {
        this.fileCode = fileCode;
        this.fileName = fileName;
    }
}
