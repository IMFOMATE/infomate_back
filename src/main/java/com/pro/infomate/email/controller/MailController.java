package com.pro.infomate.email.controller;


import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.email.dto.EmailDTO;
import com.pro.infomate.email.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

@RequestMapping("/mail")
@RestController
@Slf4j
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @GetMapping("/mailList/{memberCode}")
    public ResponseEntity<ResponseDTO> selectAddressBook(@PathVariable Integer memberCode) {


        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.OK.value()))
                        .message("success")
                        .data(mailService.selectMail(memberCode))
                        .build());

    }

    @PostMapping("/postMail")
    public ResponseEntity<ResponseDTO> sendMail(@RequestParam List receiverMail,
                                                @RequestParam String mailReference,
                                                @RequestParam String mailTitle,
                                                @RequestParam(required=false) MultipartFile mailFile,
                                                @RequestParam(required=false) Blob mailContent) {

        System.out.println("receiverMail = " + receiverMail);
        System.out.println("mailFile = " + mailFile);
        System.out.println("mailContent = " + mailContent);


        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                        .message("success")
                        .build());

    }

}

