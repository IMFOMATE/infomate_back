package com.pro.infomate.email.controller;


import com.pro.infomate.addressbook.dto.ContactDTO;
import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.email.dto.EmailDTO;
import com.pro.infomate.email.service.MailService;
import com.pro.infomate.util.FileUploadUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/mail")
@RestController
@Slf4j
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    @Value("${image.image-dir}")
    private String IMAGE_DIR;
    @Value("${image.image-url}")
    private String IMAGE_URL;

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
                                                @RequestParam List mailReference,
                                                @RequestParam String mailTitle,
                                                @RequestParam(required=false) List<MultipartFile> mailFile,
                                                @RequestParam String mailContent) {

        System.out.println("receiverMail = " + receiverMail);
        System.out.println("mailFile = " + mailFile);


        Map<String , Object> map = new HashMap<>();

        map.put("receiverMail", receiverMail);
        map.put("mailReference", mailReference);
        map.put("mailTitle", mailTitle);
        map.put("mailFile" , mailFile);
        map.put("mailContent", mailContent);

        mailService.postMail(map);





        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                        .message("success")
                        .build());

    }

    @PostMapping("/img")
    public ResponseEntity<ResponseDTO> quillImg(@RequestParam("mailContent") MultipartFile mailContent
                                                ) {

        String imageName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;

        String url;
        try {
            replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, mailContent);

            System.out.println(replaceFileName);

            url = (IMAGE_URL + replaceFileName);

            System.out.println(url);

        } catch (IOException e) {

            throw new RuntimeException(e);

        }


        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                        .message("success")
                        .data(url)
                        .build());
    }

    @GetMapping("contactList/{memberCode}")
    public ResponseEntity<ResponseDTO> selectContactList(@PathVariable Integer memberCode) {

        System.out.println("memberCode = " + memberCode);

        List<ContactDTO> contactDTO = mailService.selectContactList(memberCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                        .message("success")
                        .data(contactDTO)
                        .build());
    }


}

