package com.pro.infomate.addressbook.controller;


import com.pro.infomate.addressbook.dto.ContactDTO;
import com.pro.infomate.addressbook.service.ContactService;

import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.util.FileUploadUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/addressBook")
@RequiredArgsConstructor
@Slf4j
public class ContactController {

    private final ContactService addressBookService;



    @GetMapping("/contact/{memberCode}")
    public ResponseEntity<ResponseDTO> selectAddressBook(@PathVariable Integer memberCode) {

        log.info("[AddressBookController](addContact) memberCode : {} ", memberCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.OK.value()))
                        .message("success")
                        .data(addressBookService.selectAddressBook(memberCode))
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
        contact.setContactPhoto(contactPhoto);

        System.out.println(contact);

        addressBookService.registAddressBook(contact);



        System.out.println("contactName =============== {} " + contactName);
        System.out.println(department);
        System.out.println(contactEmail);
        System.out.println(contactPhone);
        System.out.println(companyPhone);
        System.out.println(companyAddress);
        System.out.println(memo);
        System.out.println(contactLike);
        System.out.println(contactPhoto);




        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                        .message("success")
                        .build());
    }

    @PutMapping("/contactUpdate/{contactCode}")
    public ResponseEntity<ResponseDTO> updateContact(@PathVariable Integer contactCode ) {

        log.info("[AddressBookController] updateContact contactCode {} ", contactCode);

        addressBookService.updateContact(contactCode);

            return ResponseEntity.ok()
                    .body(ResponseDTO.builder()
                            .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                            .message("success")
                            .build());

    }

    @DeleteMapping("/deleteContact/{contactCode}")
    public ResponseEntity<ResponseDTO> deleteContact(@PathVariable Integer contactCode) {

        log.info("[AddressBookController] deleteContact contactCode {} ", contactCode);

        addressBookService.deleteContact(contactCode);

        System.out.println("contactCode" + contactCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                        .message("success")
                        .build());

    }


}
