package com.pro.infomate.email.controller;

import com.pro.infomate.calendar.common.ResponseDTO;
import com.pro.infomate.email.dto.EmailDTO;
import com.pro.infomate.email.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/mail")
@RestController
@Slf4j
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @GetMapping("/mailList/{memberCode}")
    public ResponseEntity<ResponseDTO> selectAddressBook(@PathVariable Long memberCode) {


        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .data(mailService.selectMail(memberCode))
                        .build());

    }

    @PostMapping("/sendMail")
    public ResponseEntity<ResponseDTO> sendMail(@RequestBody EmailDTO email) {

        mailService.sendMail(email);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("success")
                        .build());
    }
}
