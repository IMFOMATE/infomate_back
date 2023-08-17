package com.pro.infomate.common;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;


@Getter
@ToString
public class ResponseDTO {

    private int status;

    private String message;

    private Object data;

    @Builder
    public ResponseDTO(HttpStatus status, String message, Object data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }
}
