package com.pro.infomate.exception.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class ApiExceptionDTO {

    private int status;
    private String message;

    public ApiExceptionDTO() {
    }

    public ApiExceptionDTO(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }
}
