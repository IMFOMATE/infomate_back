package com.pro.infomate.common;

import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PagingResponseDTO {

    private PageDTO pageInfo; //페이지 정보
    private String message;
    private Object data;  // 들어갈 데이터!
    private int status;

    @Builder
    public PagingResponseDTO(PageDTO pageInfo, String message, Object data, HttpStatus httpStatus) {
        this.pageInfo = pageInfo;
        this.message = message;
        this.data = data;
        this.status = httpStatus.value();
    }
}