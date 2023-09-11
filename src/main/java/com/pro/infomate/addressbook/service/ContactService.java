package com.pro.infomate.addressbook.service;

import com.pro.infomate.addressbook.dto.ContactDTO;
import com.pro.infomate.addressbook.entity.Contact;
import com.pro.infomate.addressbook.repository.ContactRepository;
import com.pro.infomate.common.Criteria;
import com.pro.infomate.exception.NotFindAddressBookException;
import com.pro.infomate.util.FileUploadUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
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


//    public List<ContactDTO> selectAddressBook(Integer memberCode) {
//
//        List<Contact> addressBookList = contactRepository.findByMemberCode(memberCode);
//
//        return addressBookList.stream().map(contact -> modelMapper.map(contact, ContactDTO.class))
//                .collect(Collectors.toList());
//
//    }

    @Transactional
    public void registAddressBook(ContactDTO contact , MultipartFile contactPhoto) {

        if(contact == null){
            new NotFindAddressBookException("연락처 등록에 실패하셨습니다.");

        }

        String replaceFileName = null;


//         임시 멤버코드
        contact.setMemberCode(2);

        String imageName = UUID.randomUUID().toString().replace("-", "");

        try {

            replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, contactPhoto);

            contact.setContactPhoto(replaceFileName);

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



    public int selectAddressBookTotal() {

        List<Contact> contactList = contactRepository.findByContactTotleMemberCode(2);




        return contactList.size();
    }

    public int selectContactLikeTotal() {

        List<Contact> contactList = contactRepository.findByContactLikeTotal('Y');

        return contactList.size();
    }



    public Object selectAddressBookListPaging(Criteria cri) {

        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();

        Pageable paging = PageRequest.of(index, count, Sort.by("contactCode").descending());

        System.out.println("paging" + paging);

        Page<Contact> result = contactRepository.findByMemberMemberCode(2, paging);

        System.out.println("result = " + result);

        List<Contact> contactList = (List<Contact>) result.getContent();

        for (int i = 0; i < contactList.size(); i++) {
            contactList.get(i).setContactPhoto(IMAGE_URL + contactList.get(i).getContactPhoto());
        }


        System.out.println("contactList = " + contactList);



        return contactList.stream().map(contact -> modelMapper.map(contact, ContactDTO.class)).collect(Collectors.toList());


    }

    public Object selectContactLikeListPaging(Criteria cri) {

        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();

        Pageable paging = PageRequest.of(index, count, Sort.by("contactCode").descending());

        System.out.println("paging" + paging);

        Page<Contact> result = contactRepository.findByContactLike('Y', paging);

        System.out.println("result = " + result);

        List<Contact> contactList = (List<Contact>) result.getContent();

        for (int i = 0; i < contactList.size(); i++) {
            contactList.get(i).setContactPhoto(IMAGE_URL + contactList.get(i).getContactPhoto());
        }


        System.out.println("contactList = " + contactList);



        return contactList.stream().map(contact -> modelMapper.map(contact, ContactDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public void updateAddressBook(ContactDTO contactDTO, MultipartFile contactPhoto) {

        Contact contact = contactRepository.findById(contactDTO.getContactCode()).get();

        contact.setContactName(contactDTO.getContactName());
        contact.setCompany(contactDTO.getCompany());
        contact.setDepartment(contactDTO.getDepartment());
        contact.setContactEmail(contactDTO.getContactEmail());
        contact.setContactPhone(contactDTO.getContactPhone());
        contact.setCompanyPhone(contactDTO.getCompanyPhone());
        contact.setCompanyAddress(contactDTO.getCompanyAddress());
        contact.setMemo(contactDTO.getMemo());

        String replaceFileName = null;


        String imageName = UUID.randomUUID().toString().replace("-", "");

        try {

            if(contactPhoto != null) {

                replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, contactPhoto);

                contact.setContactPhoto(replaceFileName);
            }



        } catch (IOException e) {
            log.info("[registAddressBook] Exception!!");
            FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);

            throw new RuntimeException(e);
        }

        System.out.println(IMAGE_URL + replaceFileName);


        System.out.println("entityContact ===============" + contact);
    }
}
