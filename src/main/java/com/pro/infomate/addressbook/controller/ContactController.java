package com.pro.infomate.addressbook.controller;


import com.pro.infomate.addressbook.dto.ContactDTO;
import com.pro.infomate.addressbook.service.ContactService;

import com.pro.infomate.common.Criteria;
import com.pro.infomate.common.PageDTO;
import com.pro.infomate.common.PagingResponseDTO;
import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.util.FileUploadUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/addressBook")
@RequiredArgsConstructor
@Slf4j
public class ContactController {


    private final ContactService contactService;



    @GetMapping("/contact/{memberCode}/{title}")
    public ResponseEntity<ResponseDTO> selectAddressBook(@PathVariable Integer memberCode
                                                        , @RequestParam(name = "offset", defaultValue = "1" , required = false) String offset
                                                        , @PathVariable String title) {

        System.out.println("title" + title);

        log.info("[AddressBookController](addContact) memberCode : {} ", memberCode);

        System.out.println("offset" + offset);

        int total = 0;

        Criteria cri = new Criteria(Integer.valueOf(offset), 10);

        Object contactList = null;

        switch (title) {
            case "전체연락처" :
                contactList = contactService.selectAddressBookListPaging(cri);
                total = contactService.selectAddressBookTotal();
                break;
            case "즐겨찾기" :
                contactList = contactService.selectContactLikeListPaging(cri);
                total = contactService.selectContactLikeTotal();
                break;
            default:
        }

        System.out.println("내가 페이징이다");
        // 페이징

        System.out.println("total" + total);



        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        /* 1. offset의 번호에 맞는 페이지에 뿌려줄 Product들 */
        pagingResponseDTO.setData(contactList);
        /* 2. PageDTO : 화면에서 페이징 처리에 필요한 정보들 */
        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));


        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.OK.value()))
                        .message("success")
                        .data(pagingResponseDTO)
                        .build());
    }


    @PostMapping("/addContact")

    public ResponseEntity<ResponseDTO> registAddressBook(@RequestParam String contactName
                                                        , @RequestParam String company
                                                        , @RequestParam String department
                                                        , @RequestParam String contactEmail
                                                        , @RequestParam String contactPhone
                                                        , @RequestParam String companyPhone
                                                        , @RequestParam String companyAddress
                                                        , @RequestParam String memo
                                                        , @RequestParam char contactLike
                                                        , @RequestParam MultipartFile contactPhoto) {


        ContactDTO contact = new ContactDTO();

        contact.setContactName(contactName);
        contact.setCompany(company);
        contact.setDepartment(department);
        contact.setContactEmail(contactEmail);
        contact.setContactPhone(contactPhone);
        contact.setCompanyPhone(companyPhone);
        contact.setCompanyAddress(companyAddress);
        contact.setMemo(memo);
        contact.setContactLike(contactLike);


        System.out.println(contact);

        contactService.registAddressBook(contact, contactPhoto);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                        .message("success")
                        .build());
    }

    @PutMapping("/addressBookUpdate/{contactCode}")
    public ResponseEntity<ResponseDTO> updateContact(@PathVariable Integer contactCode, @RequestParam(required = false) MultipartFile contactPhoto,
                                                                                        @RequestParam String contactName,
                                                                                        @RequestParam String company,
                                                                                        @RequestParam String department,
                                                                                        @RequestParam String contactEmail,
                                                                                        @RequestParam String contactPhone,
                                                                                        @RequestParam String companyPhone,
                                                                                        @RequestParam String companyAddress,
                                                                                        @RequestParam String memo
    ) {

        System.out.println("contactPhoto = " + contactPhoto);
        System.out.println("contactName = " + contactName);

        ContactDTO contactDTO = new ContactDTO();

        contactDTO.setContactName(contactName);
        contactDTO.setCompany(company);
        contactDTO.setDepartment(department);
        contactDTO.setContactEmail(contactEmail);
        contactDTO.setContactPhone(contactPhone);
        contactDTO.setCompanyPhone(companyPhone);
        contactDTO.setCompanyAddress(companyAddress);
        contactDTO.setMemo(memo);
        contactDTO.setContactCode(contactCode);

        contactService.updateAddressBook(contactDTO, contactPhoto);

        log.info("[AddressBookController] updateContact contactCode {} ", contactCode);

        System.out.println("contactCode = " + contactCode);


            return ResponseEntity.ok()
                    .body(ResponseDTO.builder()
                            .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                            .message("success")
                            .build());

    }

    @PutMapping("/contactUpdate/{contactCode}")
    public ResponseEntity<ResponseDTO> updateContact(@PathVariable Integer contactCode) {


        log.info("[AddressBookController] updateContact contactCode {} ", contactCode);

        System.out.println("contactCode = " + contactCode);

        contactService.updateContact(contactCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                        .message("success")
                        .build());

    }

    @DeleteMapping("/deleteContact/{contactCode}")
    public ResponseEntity<ResponseDTO> deleteContact(@PathVariable Integer contactCode) {

        log.info("[AddressBookController] deleteContact contactCode {} ", contactCode);

        contactService.deleteContact(contactCode);

        System.out.println("contactCode" + contactCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                        .message("success")
                        .build());

    }


}
