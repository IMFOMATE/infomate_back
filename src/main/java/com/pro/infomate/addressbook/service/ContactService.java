package com.pro.infomate.addressbook.service;

import com.pro.infomate.addressbook.dto.ContactDTO;
import com.pro.infomate.addressbook.entity.Contact;
import com.pro.infomate.addressbook.repository.ContactRepository;
import com.pro.infomate.exception.NotFindAddressBookException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;



@Service
@Slf4j
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;


    public List<ContactDTO> selectAddressBook(int memberCode) {

        List<Contact> addressBookList = contactRepository.findByMemberCode(memberCode);

        return addressBookList.stream().map(contact -> modelMapper.map(contact, ContactDTO.class))
                .collect(Collectors.toList());

    }

    @Transactional
    public void registAddressBook(ContactDTO contact) {

        if(contact == null){
            new NotFindAddressBookException("연락처 등록에 실패하셨습니다.");

        }

        contact.setMemberCode(2);

        System.out.println("기;뭐륑"+contact.getContactPhoto());

        Contact entityContact = modelMapper.map(contact, Contact.class);


        contactRepository.save(entityContact);

    }

    @Transactional
    public void updateContact(Long contactCode) {

        if (contactCode !=null) {

            Contact addressBookList = contactRepository.findById(contactCode).get();

            if(addressBookList.getContactLike() == 'N') {
                addressBookList.setContactLike('Y');
            } else if(addressBookList.getContactLike() == 'Y') {
                addressBookList.setContactLike('N');
            }
            System.out.println("======" + addressBookList.getContactLike());

        }

    }
}
