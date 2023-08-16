package com.pro.infomate.calendar.common;


import lombok.*;
import org.springframework.http.HttpStatus;


@Getter
@ToString
@Builder(toBuilder = true)
public class ResponseDTO {

    private int status;

    private String message;

    private Object data;

}
