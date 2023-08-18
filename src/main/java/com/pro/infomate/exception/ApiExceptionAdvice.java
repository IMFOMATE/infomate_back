package com.pro.infomate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler(NotFindDataException.class)
    public ResponseEntity<Response> notFindDataException(NotFindDataException e){
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new Response(HttpStatus.NO_CONTENT,"fail", e.getMessage()));
    }

    @ExceptionHandler(NotEnoughDateException.class)
    public ResponseEntity<Response> notEnoughDataException(NotEnoughDateException e){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new Response(HttpStatus.NOT_ACCEPTABLE,"fail", e.getMessage()));
    }

    @ExceptionHandler(NotAuthenticationServer.class)
    public ResponseEntity<Response> notAuthenticationServer(NotAuthenticationServer e){
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new Response(HttpStatus.NO_CONTENT,"fail",e.getMessage()));
    }

}
