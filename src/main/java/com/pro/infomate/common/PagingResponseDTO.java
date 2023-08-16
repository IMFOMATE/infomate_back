package com.pro.infomate.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@ToString
public class PagingResponseDTO {

    private PageDTO pageInfo; //페이지 정보
    private Object data;  // 들어갈 데이터!

}