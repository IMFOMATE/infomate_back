package com.pro.infomate.approval.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.infomate.approval.entity.DocumentStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class DocumentDTO {

    private Long id;

    @NotBlank
    @Size(min = 5, max = 100)
    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    private DocumentStatus documentStatus;

    private String content;

    private String documentKind;

    private List<DocFileDTO> fileList = new ArrayList<>();

    private List<ApprovalDTO> approvalList = new ArrayList<>();;


    public DocumentDTO(Long id, String title, LocalDateTime createdDate, DocumentStatus documentStatus, String content, String documentKind, List<DocFileDTO> fileList, List<ApprovalDTO> approvalList) {
        this.id = id;
        this.title = title;
        this.createdDate = createdDate;
        this.documentStatus = documentStatus;
        this.content = content;
        this.documentKind = documentKind;
        this.fileList = fileList;
        this.approvalList = approvalList;
    }
}
