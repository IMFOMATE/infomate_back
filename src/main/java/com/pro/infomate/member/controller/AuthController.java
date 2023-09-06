package com.pro.infomate.member.controller;

import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.member.service.AuthService;
import com.pro.infomate.member.service.RegistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private final RegistService registService;

    public AuthController(AuthService authService, RegistService registService) {
        this.authService = authService;
        this.registService = registService;
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

    @PostMapping("/regist")
    public ResponseEntity<ResponseDTO> registMember(@ModelAttribute MemberDTO memberDTO, MultipartFile memberImage){

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "회원 등록 성공"
                        , registService.registMember(memberDTO, memberImage)));
    }
}
