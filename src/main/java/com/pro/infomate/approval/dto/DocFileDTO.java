package com.pro.infomate.approval.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
public class DocFileDTO {

    private Long fileCode;

    @NotBlank
    private String fileName;

    private String originalName;

    @Builder
    public DocFileDTO(Long fileCode, String fileName, String originalName) {
        this.fileCode = fileCode;
        this.fileName = fileName;
        this.originalName = originalName;
    }
}
