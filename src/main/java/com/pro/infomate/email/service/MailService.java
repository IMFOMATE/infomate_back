package com.pro.infomate.email.service;

import com.pro.infomate.addressbook.entity.Contact;
import com.pro.infomate.email.dto.EmailDTO;
import com.pro.infomate.email.entity.Email;
import com.pro.infomate.email.repository.EmailAndMemberRepository;
import com.pro.infomate.email.repository.MailReferenceRepository;
import com.pro.infomate.email.repository.PhotoFileRepository;
import com.pro.infomate.email.repository.TrashRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    private final EmailAndMemberRepository emailAndMemberRepository;
    private final MailReferenceRepository mailReferenceRepository;
    private final PhotoFileRepository photoFileRepository;
    private final TrashRepository trashRepository;

    private final ModelMapper modelMapper;


    public List<EmailDTO> selectMail(Long memberCode) {

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
}
