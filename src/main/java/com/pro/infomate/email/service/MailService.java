package com.pro.infomate.email.service;

import com.pro.infomate.addressbook.dto.ContactDTO;
import com.pro.infomate.addressbook.entity.Contact;
import com.pro.infomate.addressbook.repository.ContactRepository;
import com.pro.infomate.email.dto.EmailDTO;
import com.pro.infomate.email.dto.PhotoFileDTO;
import com.pro.infomate.email.entity.Email;
import com.pro.infomate.email.entity.PhotoFile;
import com.pro.infomate.email.repository.EmailAndMemberRepository;
import com.pro.infomate.email.repository.MailReferenceRepository;
import com.pro.infomate.email.repository.PhotoFileRepository;
import com.pro.infomate.email.repository.TrashRepository;
import com.pro.infomate.util.FileUploadUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    @Value("${image.image-dir}")
    private String IMAGE_DIR;
    @Value("${image.image-url}")
    private String IMAGE_URL;

    private final EmailAndMemberRepository emailAndMemberRepository;
    private final MailReferenceRepository mailReferenceRepository;
    private final PhotoFileRepository photoFileRepository;
    private final TrashRepository trashRepository;
    private final ContactRepository contactRepository;

    private final ModelMapper modelMapper;


    public List<EmailDTO> selectMail(Integer memberCode) {

        List<Email> emails = emailAndMemberRepository.findByMemberCode(memberCode);


        return emails.stream().map(email -> modelMapper.map(email, EmailDTO.class)).collect(Collectors.toList());

    }

    public void sendMail(EmailDTO email) {

        if (email == null) {
            new RuntimeException("메일 전송 실패");
        }

        Email entityEmail = modelMapper.map(email, Email.class);

        emailAndMemberRepository.save(entityEmail);

    }


    public List<ContactDTO> selectContactList(Integer memberCode) {

        List<Contact> contactList = contactRepository.findByContactTotleMemberCode(memberCode);

        System.out.println("contact = " + contactList );

        return contactList.stream().map(contact -> modelMapper.map(contact, ContactDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public void postMail(Map<String, Object> map) {


        // 파일 Entity
        PhotoFile mailFileEntity = null;

        // 이메일
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setMemberCode(2);
        emailDTO.setMailContent((String) map.get("mailContent"));
        emailDTO.setMailTitle((String) map.get("mailTitle"));
        emailDTO.setMailDate(new Date());
        emailDTO.setMailStatus('N');

        // 이메일 Entity
        Email save = null;

        // 파일
        PhotoFileDTO photoFile = new PhotoFileDTO();

        List<Object> member = (List) map.get("receiverMail");


        for (int i = 0; i < member.size() ; i++) {

            Contact contacts  = contactRepository.findByContactName(member.get(i));

            emailDTO.setReceiverMail(contacts.getContactEmail());

            Email emailEntity = modelMapper.map(emailDTO , Email.class);

            // 메일보내기
            save = emailAndMemberRepository.save(emailEntity);

            // 파일저장
            photoFile.setMailCode(save.getMailCode());

            photoFile.setFileDate(new Date());

            String replaceFileName = null;

            String imageName = UUID.randomUUID().toString().replace("-", "");


            List<MultipartFile> mailFile = (List) map.get("mailFile");

            System.out.println("photoFIle" + photoFile);

            System.out.println("하" + mailFile);


            for (int y =0; y< mailFile.size(); y++) {

                System.out.println("힘들다"+ mailFile.get(y) );

                try {

                    replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, mailFile.get(i));

                    photoFile.setFileModificationName(replaceFileName);

                    mailFileEntity = modelMapper.map(photoFile , PhotoFile.class);


                    photoFileRepository.save(mailFileEntity);



                } catch (IOException e) {

                    log.info("[registAddressBook] Exception!!");
                    FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);

                    throw new RuntimeException(e);
                }
            }


        }






    }
}
