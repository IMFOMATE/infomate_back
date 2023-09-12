package com.pro.infomate.email.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pro.infomate.addressbook.dto.ContactDTO;
import com.pro.infomate.common.Criteria;
import com.pro.infomate.common.PageDTO;
import com.pro.infomate.common.PagingResponseDTO;
import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.email.dto.EmailDTO;
import com.pro.infomate.email.dto.PhotoFileDTO;
import com.pro.infomate.email.entity.Email;
import com.pro.infomate.email.service.MailService;
import com.pro.infomate.member.dto.MemberDTO;
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

    @GetMapping("/mailList/{memberCode}/{title}")
    public ResponseEntity<ResponseDTO> selectAddressBook(@PathVariable Integer memberCode
                                                        , @RequestParam(name = "offset", defaultValue = "1" , required = false) String offset
                                                        , @PathVariable String title) {

        System.out.println("memberCode " + memberCode);

        System.out.println("title" + title);

        int total = 0;

        Criteria cri = new Criteria(Integer.valueOf(offset), 10);

        Object mailList = null;

        switch (title) {

            case "전체메일함":
                mailList = mailService.selectAllMail(memberCode,cri);
                total = mailService.selectMailTotal(memberCode);
                break;
            case "안읽은 메일함":
                mailList = mailService.selectUnReadMail(memberCode, cri);
                total = mailService.selectUnReadMailTotal(memberCode);
                break;
            case "읽은 메일함":
                mailList = mailService.selectReadMail(memberCode, cri);
                total = mailService.selectReadMailTotal(memberCode);

                break;
            case "참조 메일함": break;
            case "휴지통": break;
        }

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        /* 1. offset의 번호에 맞는 페이지에 뿌려줄 Product들 */
        pagingResponseDTO.setData(mailList);
        /* 2. PageDTO : 화면에서 페이징 처리에 필요한 정보들 */
        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));



        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.OK.value()))
                        .message("success")
                        .data(pagingResponseDTO)
                        .build());

    }

    @PostMapping("/postMail")
    public ResponseEntity<ResponseDTO> sendMail(@RequestParam Integer memberCode,
                                                @RequestParam List receiverMail,

                                                @RequestParam String mailTitle,
                                                @RequestParam(required=false) List<MultipartFile> mailFile,
                                                @RequestParam String mailContent) {

        System.out.println("receiverMail = " + receiverMail);
        System.out.println("mailFile = " + mailFile);
        System.out.println("memberCode = " + memberCode);


        Map<String , Object> map = new HashMap<>();

        map.put("memberCode", memberCode);
        map.put("receiverMail", receiverMail);

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

    @GetMapping("selectMember")
    public ResponseEntity<ResponseDTO> selectMember(MemberDTO member) {

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                        .message("success")
                        .data(mailService.selectMember())
                        .build());

    }

    @DeleteMapping("deleteMail/{mailCode}")
    public ResponseEntity<ResponseDTO> deleteMail(@PathVariable Integer mailCode) {

        System.out.println("mailCode"  + mailCode);

        mailService.deleteMail(mailCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                        .message("success")
                        .build());
    }

    @PutMapping("updateMail/{mailCode}")
    public ResponseEntity<ResponseDTO> updateMail(@PathVariable Integer mailCode) {

        System.out.println("mailCode = " + mailCode);

        mailService.updateMail(mailCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                        .message("success")
                        .build());
    }

    @GetMapping("selectTrash/{memberCode}")
    public ResponseEntity<ResponseDTO> selectTrash(@PathVariable Integer memberCode) {

        System.out.println("memberCode "  + memberCode);

        Map<String ,Object> email = mailService.selectTrash(memberCode);

        System.out.println("tq" + email);


        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                        .message("success")
                        .data(email)
                        .build());
    }

    @DeleteMapping("deleteTrash/{memberCode}")
    public ResponseEntity<ResponseDTO> deleteTrash(@PathVariable Integer memberCode) {

        System.out.println("memberCode = " + memberCode);

        mailService.deleteTrash(memberCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                        .message("success")
                        .build());
    }

    @PutMapping("updateTrash/{memberCode}")
    public ResponseEntity<ResponseDTO> updateTrash(@PathVariable Integer memberCode) {

        System.out.println("memberCode = " + memberCode);

        mailService.updateTrash(memberCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                        .message("success")
                        .build());
    }


    @GetMapping("selectFile/{mailCode}")
    public ResponseEntity<ResponseDTO> selectMailFile(@PathVariable Integer mailCode) {

        System.out.println("mailCode = " + mailCode);

        List<PhotoFileDTO> file = mailService.selectMailFile(mailCode);





        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                        .message("success")
                        .data(file)
                        .build());
    }

}

