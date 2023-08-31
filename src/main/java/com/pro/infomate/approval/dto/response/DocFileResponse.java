package com.pro.infomate.approval.dto.response;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
public class DocFileResponse {

    private Long fileCode; // 파일 코드

    private String originalName; // 파일명

    private String fileName; // 파일명

    private String fileType; // 파일 타입

    private long fileSize; // 파일 사이즈

    @Builder
    public DocFileResponse(Long fileCode, String originalName, String fileName, String fileType, long fileSize) {
        this.fileCode = fileCode;
        this.originalName = originalName;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
}
