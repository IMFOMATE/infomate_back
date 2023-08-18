package com.pro.infomate.addressbook.service;

import com.pro.infomate.addressbook.dto.ContactDTO;
import com.pro.infomate.addressbook.entity.Contact;
import com.pro.infomate.addressbook.repository.ContactRepository;
import com.pro.infomate.calendar.entity.Calendar;
import com.pro.infomate.exception.NotFindDataException;
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


    public List<ContactDTO> selectAddressBook(Long memberCode) {

        List<Contact> addressBookList = contactRepository.findByMemberCode(memberCode);

        return addressBookList.stream().map(contact -> modelMapper.map(contact, ContactDTO.class))
                .collect(Collectors.toList());

    }

    public void registAddressBook(ContactDTO contact) {

        if(contact != null){
            List<Contact> contactList = contactRepository.findByMemberCode(Long.valueOf(contact.getMemberCode()));

            if(contactList.isEmpty()) throw new NotFindDataException("주소록을 찾을수 없습니다.");


        }

        Contact entityContact = modelMapper.map(contact, Contact.class);

        contactRepository.save(entityContact);

    }
}
