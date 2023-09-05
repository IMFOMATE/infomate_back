package com.pro.infomate.Board.dto;

import com.pro.infomate.common.PageDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PagingResponseDTO {

    private Object data;

    private PageDTO pageInfo;

}
