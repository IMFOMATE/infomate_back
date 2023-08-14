package com.pro.infomate.addressbook.controller;

import com.pro.infomate.addressbook.common.ResponseDTO;
import com.pro.infomate.addressbook.service.AddressBookService;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/addressBook")
@RequiredArgsConstructor
@Slf4j
public class AddressBookController {

    private final AddressBookService addressBookService;



    @GetMapping("/contact/{memberCode}")
    public ResponseEntity<ResponseDTO> selectAddressBook(@PathVariable Integer memberCode) {

        log.info("[AddressBookController](addContact) memberCode : {} ", memberCode);

        return ResponseEntity.ok()
                .body(com.pro.infomate.addressbook.common.ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .data(addressBookService.selectAddressBook(memberCode))
                        .build());

    }
}
