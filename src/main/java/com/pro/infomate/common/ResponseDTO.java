package com.pro.infomate.common;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@Builder
public class ResponseDTO {

    private int status;

    private String message;

    private Object data;

}
