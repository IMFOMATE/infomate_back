package com.pro.infomate.member.controller;

import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.member.service.RegistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class RegistController {

    private final RegistService registService;

    public RegistController(RegistService registService) {
        this.registService = registService;
    }

    @PostMapping("/regist")
    public ResponseEntity<ResponseDTO> registMember(@ModelAttribute MemberDTO memberDTO, MultipartFile memberImage){

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "회원 등록 성공"
                                    , registService.registMember(memberDTO, memberImage)));
    }








}
