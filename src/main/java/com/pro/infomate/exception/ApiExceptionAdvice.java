package com.pro.infomate.exception;

import lombok.extern.slf4j.Slf4j;
import com.pro.infomate.exception.dto.ApiExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
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

        log.info("[ApiExceptionAdvice](notFindDataException) error : {} ", e);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new Response(HttpStatus.NO_CONTENT,"fail", e.getMessage()));
    }

    @ExceptionHandler(NotEnoughDateException.class)
    public ResponseEntity<Response> notEnoughDataException(NotEnoughDateException e){

        log.info("[ApiExceptionAdvice](notEnoughDataException) erroer : {} ", e);

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new Response(HttpStatus.NOT_ACCEPTABLE,"fail", e.getMessage()));
    }

    @ExceptionHandler(NotAuthenticationServer.class)
    public ResponseEntity<Response> notAuthenticationServer(NotAuthenticationServer e){

        log.info("[ApiExceptionAdvice](notAuthenticationServer) error : {} ", e);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new Response(HttpStatus.NO_CONTENT,"fail",e.getMessage()));
    }

    @ExceptionHandler(AlreadyRequstException.class)
    public ResponseEntity<Response> AlreadyRequestException(AlreadyRequstException e){

        log.info("[ApiExceptionAdvice](AlreadyRequestException) error : {} ", e);

        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                .body(new Response(HttpStatus.ALREADY_REPORTED, "fail", e.getMessage()));
    }

}
