package com.pro.infomate.addressbook.service;

import com.pro.infomate.addressbook.dto.ContactDTO;
import com.pro.infomate.addressbook.entity.Contact;
import com.pro.infomate.addressbook.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressBookService {

    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;


    public List<ContactDTO> selectAddressBook(Integer memberCode) {

        Optional<Contact> addressBookList = contactRepository.findById(memberCode);

        return addressBookList.stream().map(contact -> modelMapper.map(contact, ContactDTO.class))
                .collect(Collectors.toList());

    }
}
