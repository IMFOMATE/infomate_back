package com.pro.infomate.email.service;

import com.pro.infomate.addressbook.dto.ContactDTO;
import com.pro.infomate.addressbook.entity.Contact;
import com.pro.infomate.addressbook.repository.ContactRepository;
import com.pro.infomate.common.Criteria;
import com.pro.infomate.email.dto.EmailDTO;
import com.pro.infomate.email.dto.PhotoFileDTO;
import com.pro.infomate.email.dto.TrashDTO;
import com.pro.infomate.email.entity.Email;
import com.pro.infomate.email.entity.PhotoFile;
import com.pro.infomate.email.entity.Trash;
import com.pro.infomate.email.repository.EmailAndMemberRepository;
import com.pro.infomate.email.repository.MailReferenceRepository;
import com.pro.infomate.email.repository.PhotoFileRepository;
import com.pro.infomate.email.repository.TrashRepository;
import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final MemberRepository memberRepository;

    private final ModelMapper modelMapper;


    public Map<String ,Object> selectMail(Integer memberCode) {

        List<Member> memberEntity = memberRepository.findAllById(Collections.singleton(memberCode));
        List<Email> emailEntity = emailAndMemberRepository.findAll();

        List<Email> matchingEmails = new ArrayList<>();

        List<Optional<Member>> sendMemberName = new ArrayList<>();

        for (Member member : memberEntity) {
            for (Email email : emailEntity) {
                if (member.getMemberEmail().equals(email.getReceiverMail())) {
                    matchingEmails.add(email);
                }
            }
        }


        System.out.println("matchingEmails" + matchingEmails);

        for (int i = 0; i < matchingEmails.size(); i++) {

            Optional<Member> member = memberRepository.findById(matchingEmails.get(i).getMember().getMemberCode());

            sendMemberName.add(member);
        }

        System.out.println("sendMemberName" + sendMemberName);

        matchingEmails.stream().map(email -> modelMapper.map(email, EmailDTO.class)).collect(Collectors.toList());
        sendMemberName.stream().map(member -> modelMapper.map(member, MemberDTO.class)).collect(Collectors.toList());

        List<EmailDTO> emailDTO =  matchingEmails.stream().map(email -> modelMapper.map(email, EmailDTO.class)).collect(Collectors.toList());;
        List<MemberDTO> memberDTO = sendMemberName.stream().map(member -> modelMapper.map(member, MemberDTO.class)).collect(Collectors.toList());

        Map<String , Object> map = new HashMap<>();
        map.put("matchingEmails", emailDTO);
        map.put("sendMemberName", memberDTO);

        System.out.println("map" + map.get("matchingEmails"));
        System.out.println("giwon" + map.get("sendMemberName"));



        return map;

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

            if(contacts == null) {
//                Member memberEntity = memberRepository.findByMemberName(member.get(i));

//                emailDTO.setReceiverMail(memberEntity.getMemberEmail());
            }
            emailDTO.setReceiverMail(contacts.getContactEmail());

            Email emailEntity = modelMapper.map(emailDTO , Email.class);

            System.out.println("찐막" + emailEntity.getReceiverMail());

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

    public List<MemberDTO> selectMember() {

        List<Member> memberList =  memberRepository.findAll();

        System.out.println("memberList" + memberList);



        return memberList.stream().map(member -> modelMapper.map(member , MemberDTO.class)).collect(Collectors.toList());

    }

    public Object selectAllMail(Integer memberCode, Criteria cri) {

        List<Member> memberEntity = memberRepository.findAllById(Collections.singleton(memberCode));
        List<Email> emailEntity = emailAndMemberRepository.findAll();

        List<Email> matchingEmails = new ArrayList<>();

        List<Optional<Member>> sendMemberName = new ArrayList<>();

        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();

        Pageable paging = PageRequest.of(index, count, Sort.by("memberCode").descending());

        System.out.println("paging" + paging);

        Page<Member> result = memberRepository.findByMemberCode(memberCode, paging);

        System.out.println("result " + result);

        List<Member> memberList = (List<Member>) result.getContent();




        for (Member member : memberList) {
            for (Email email : emailEntity) {
                if (member.getMemberEmail().equals(email.getReceiverMail())) {
                    matchingEmails.add(email);
                }
            }
        }


        System.out.println("matchingEmails" + matchingEmails);

        for (int i = 0; i < matchingEmails.size(); i++) {

            Optional<Member> member = memberRepository.findById(matchingEmails.get(i).getMember().getMemberCode());

            sendMemberName.add(member);
        }

        System.out.println("sendMemberName" + sendMemberName);

        matchingEmails.stream().map(email -> modelMapper.map(email, EmailDTO.class)).collect(Collectors.toList());
        sendMemberName.stream().map(member -> modelMapper.map(member, MemberDTO.class)).collect(Collectors.toList());

        List<EmailDTO> emailDTO =  matchingEmails.stream().map(email -> modelMapper.map(email, EmailDTO.class)).collect(Collectors.toList());;
        List<MemberDTO> memberDTO = sendMemberName.stream().map(member -> modelMapper.map(member, MemberDTO.class)).collect(Collectors.toList());

        Map<String , Object> map = new HashMap<>();
        map.put("matchingEmails", emailDTO);
        map.put("sendMemberName", memberDTO);

        System.out.println("map" + map.get("matchingEmails"));
        System.out.println("giwon" + map.get("sendMemberName"));

        return map;
    }


    public int selectMailTotal(Integer memberCode) {

        List<Member> memberEntity = memberRepository.findAllById(Collections.singleton(memberCode));
        List<Email> emailEntity = emailAndMemberRepository.findAll();

        List<Email> matchingEmails = new ArrayList<>();

        for (Member member : memberEntity) {
            for (Email email : emailEntity) {
                if (member.getMemberEmail().equals(email.getReceiverMail())) {
                    matchingEmails.add(email);
                }
            }
        }

        System.out.println("size" + matchingEmails.size());

        return matchingEmails.size();

    }

    public Object selectUnReadMail(Integer memberCode, Criteria cri) {

        List<Email> emailEntity = emailAndMemberRepository.findAll();

        List<Email> matchingEmails = new ArrayList<>();

        List<Optional<Member>> sendMemberName = new ArrayList<>();

        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();

        Pageable paging = PageRequest.of(index, count, Sort.by("memberCode").descending());

        System.out.println("paging" + paging);

        Page<Member> result = memberRepository.findByMemberCode(memberCode , paging);

        System.out.println("result " + result);

        List<Member> memberList = (List<Member>) result.getContent();




        for (Member member : memberList) {
            for (Email email : emailEntity) {
                if (member.getMemberEmail().equals(email.getReceiverMail())) {
                    System.out.println("상태값"+email.getMailStatus());
                    if(email.getMailStatus() == 'N') {
                        matchingEmails.add(email);
                    }
                }
            }
        }


        System.out.println("matchingEmails" + matchingEmails);

        for (int i = 0; i < matchingEmails.size(); i++) {

            Optional<Member> member = memberRepository.findById(matchingEmails.get(i).getMember().getMemberCode());

            sendMemberName.add(member);
        }

        System.out.println("sendMemberName" + sendMemberName);

        matchingEmails.stream().map(email -> modelMapper.map(email, EmailDTO.class)).collect(Collectors.toList());
        sendMemberName.stream().map(member -> modelMapper.map(member, MemberDTO.class)).collect(Collectors.toList());

        List<EmailDTO> emailDTO =  matchingEmails.stream().map(email -> modelMapper.map(email, EmailDTO.class)).collect(Collectors.toList());;
        List<MemberDTO> memberDTO = sendMemberName.stream().map(member -> modelMapper.map(member, MemberDTO.class)).collect(Collectors.toList());

        Map<String , Object> map = new HashMap<>();
        map.put("matchingEmails", emailDTO);
        map.put("sendMemberName", memberDTO);

        System.out.println("map" + map.get("matchingEmails"));
        System.out.println("giwon" + map.get("sendMemberName"));

        return map;
    }

    public int selectUnReadMailTotal(Integer memberCode) {

        List<Member> memberEntity = memberRepository.findAllById(Collections.singleton(memberCode));
        List<Email> emailEntity = emailAndMemberRepository.findAll();

        List<Email> matchingEmails = new ArrayList<>();

        for (Member member : memberEntity) {
            for (Email email : emailEntity) {
                if (member.getMemberEmail().equals(email.getReceiverMail())) {
                    if(email.getMailStatus() == 'N') {
                        matchingEmails.add(email);
                    }

                }
            }
        }

        System.out.println("size" + matchingEmails.size());

        return matchingEmails.size();
    }

    public Object selectReadMail(Integer memberCode, Criteria cri) {

        List<Email> emailEntity = emailAndMemberRepository.findAll();

        List<Email> matchingEmails = new ArrayList<>();

        List<Optional<Member>> sendMemberName = new ArrayList<>();

        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();

        Pageable paging = PageRequest.of(index, count, Sort.by("memberCode").descending());

        System.out.println("paging" + paging);

        Page<Member> result = memberRepository.findByMemberCode(memberCode , paging);

        System.out.println("result " + result);

        List<Member> memberList = (List<Member>) result.getContent();



        for (Member member : memberList) {
            for (Email email : emailEntity) {
                if (member.getMemberEmail().equals(email.getReceiverMail())) {
                    System.out.println("상태값"+email.getMailStatus());
                    if(email.getMailStatus() == 'Y') {
                        matchingEmails.add(email);
                    }
                }
            }
        }


        System.out.println("matchingEmails" + matchingEmails);

        for (int i = 0; i < matchingEmails.size(); i++) {

            Optional<Member> member = memberRepository.findById(matchingEmails.get(i).getMember().getMemberCode());

            sendMemberName.add(member);
        }

        System.out.println("sendMemberName" + sendMemberName);

        matchingEmails.stream().map(email -> modelMapper.map(email, EmailDTO.class)).collect(Collectors.toList());
        sendMemberName.stream().map(member -> modelMapper.map(member, MemberDTO.class)).collect(Collectors.toList());

        List<EmailDTO> emailDTO =  matchingEmails.stream().map(email -> modelMapper.map(email, EmailDTO.class)).collect(Collectors.toList());;
        List<MemberDTO> memberDTO = sendMemberName.stream().map(member -> modelMapper.map(member, MemberDTO.class)).collect(Collectors.toList());

        Map<String , Object> map = new HashMap<>();
        map.put("matchingEmails", emailDTO);
        map.put("sendMemberName", memberDTO);

        System.out.println("map" + map.get("matchingEmails"));
        System.out.println("giwon" + map.get("sendMemberName"));

        return map;
    }

    public int selectReadMailTotal(Integer memberCode) {

        List<Member> memberEntity = memberRepository.findAllById(Collections.singleton(memberCode));
        List<Email> emailEntity = emailAndMemberRepository.findAll();

        List<Email> matchingEmails = new ArrayList<>();

        for (Member member : memberEntity) {
            for (Email email : emailEntity) {
                if (member.getMemberEmail().equals(email.getReceiverMail())) {
                    if(email.getMailStatus() == 'Y')
                    matchingEmails.add(email);
                }
            }
        }

        System.out.println("size" + matchingEmails.size());

        return matchingEmails.size();

    }

    public void deleteMail(Integer mailCode) {

        Email email = emailAndMemberRepository.findById(mailCode).get();

        Trash trash = new Trash();
        trash.setMail(email);

        trashRepository.save(trash);



    }
}
