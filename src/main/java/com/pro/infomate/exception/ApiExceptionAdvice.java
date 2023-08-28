package com.pro.infomate.exception;

import com.pro.infomate.exception.dto.ApiExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity<ApiExceptionDTO> exceptionHandler(LoginFailException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiExceptionDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ApiExceptionDTO> exceptionHandler(TokenException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiExceptionDTO(HttpStatus.UNAUTHORIZED, e.getMessage()));
    }

    @ExceptionHandler(DuplicatedMemberEmailException.class)
    public ResponseEntity<ApiExceptionDTO> exceptionHandler(DuplicatedMemberEmailException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiExceptionDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(NotFindDataException.class)
    public ResponseEntity<Response> notFindDataException(NotFindDataException e){
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new Response(HttpStatus.NO_CONTENT,"fail", e.getMessage()));
    }

    @ExceptionHandler(NotAuthenticationServer.class)
    public ResponseEntity<Response> notAuthenticationServer(NotAuthenticationServer e){
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new Response(HttpStatus.NO_CONTENT,"fail",e.getMessage()));
    }
    
}
