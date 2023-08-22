package com.pro.infomate.addressbook.controller;

import com.pro.infomate.addressbook.common.ResponseDTO;
import com.pro.infomate.addressbook.dto.ContactDTO;
import com.pro.infomate.addressbook.service.AddressBookService;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addressBook")
@RequiredArgsConstructor
@Slf4j
public class AddressBookController {

    private final AddressBookService addressBookService;



    @GetMapping("/contact/{memberCode}")
    public ResponseEntity<ResponseDTO> selectAddressBook(@PathVariable Long memberCode) {

        log.info("[AddressBookController](addContact) memberCode : {} ", memberCode);

        return ResponseEntity.ok()
                .body(com.pro.infomate.addressbook.common.ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .data(addressBookService.selectAddressBook(memberCode))
                        .build());

    }

    @PostMapping("/addContact")
    public ResponseEntity<ResponseDTO> registAddressBook(@RequestBody ContactDTO contact) {

        log.info("contact ====================={} " , contact);
        addressBookService.registAddressBook(contact);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("success")
                        .build());
    }


}
