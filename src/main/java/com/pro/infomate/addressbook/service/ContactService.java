package com.pro.infomate.addressbook.service;

import com.pro.infomate.addressbook.dto.ContactDTO;
import com.pro.infomate.addressbook.entity.Contact;
import com.pro.infomate.addressbook.repository.ContactRepository;
import com.pro.infomate.exception.NotFindAddressBookException;
import com.pro.infomate.util.FileUploadUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;




@Service
@Slf4j
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;


    @Value("${image.image-dir}")
    private String IMAGE_DIR;
    @Value("${image.image-url}")
    private String IMAGE_URL;


    public List<ContactDTO> selectAddressBook(Integer memberCode) {

        List<Contact> addressBookList = contactRepository.findByMemberCode(memberCode);

        return addressBookList.stream().map(contact -> modelMapper.map(contact, ContactDTO.class))
                .collect(Collectors.toList());

    }

    @Transactional
    public void registAddressBook(ContactDTO contact) {

        if(contact == null){
            new NotFindAddressBookException("연락처 등록에 실패하셨습니다.");

        }

        String replaceFileName = null;


//         임시 멤버코드
        contact.setMemberCode(2);

        String imageName = UUID.randomUUID().toString().replace("-", "");

        try {

            replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, contact.getContactPhoto());

        } catch (IOException e) {
            log.info("[registAddressBook] Exception!!");
            FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);

            throw new RuntimeException(e);
        }


        Contact entityContact = modelMapper.map(contact, Contact.class);

        entityContact.setContactPhoto(replaceFileName);

        System.out.println("entityContact ===============" +entityContact);




        contactRepository.save(entityContact);

    }

    @Transactional
    public void updateContact(Integer contactCode) {

        if (contactCode !=null) {

            Contact contact = contactRepository.findById(contactCode).get();

            if(contact.getContactLike() == 'N') {
                contact.setContactLike('Y');
            } else if(contact.getContactLike() == 'Y') {
                contact.setContactLike('N');
            }
            System.out.println("======" + contact.getContactLike());

        }

    }

    public void deleteContact(Integer contactCode) {

        if (contactCode != null ) {

            Contact contact = contactRepository.findById(contactCode).get();

            contactRepository.delete(contact);
        }
    }
}
