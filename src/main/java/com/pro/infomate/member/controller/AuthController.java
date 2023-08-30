package com.pro.infomate.member.controller;

import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.member.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody MemberDTO memberDTO){

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK, "로그인 성공", authService.login(memberDTO)));
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> signup(@RequestBody MemberDTO memberDTO){
        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.CREATED, "회원가입 성공", authService.signup(memberDTO)));
    }
}
