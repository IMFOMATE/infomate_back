package com.pro.infomate.member.controller;

import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.member.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/regist")
    public ResponseEntity<ResponseDTO> signup(@ModelAttribute MemberDTO memberDTO,
                                              MultipartFile image){
        System.out.println("memberDTO = " + memberDTO);
        System.out.println("memberPic = " + image);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.CREATED, "회원가입 성공", authService.regist(memberDTO, image)));
    }


}
