package com.pro.infomate.approval.entity;

import com.pro.infomate.addressbook.entity.Contact;
import com.pro.infomate.addressbook.repository.ContactRepository;
import com.pro.infomate.email.entity.Email;
import com.pro.infomate.email.repository.EmailAndMemberRepository;
import com.pro.infomate.exception.NotFindDataException;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Commit
public class AddressBookTest {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EmailAndMemberRepository emailAndMemberRepository;

    @Test
    @DisplayName("contact test")
    void test1() {

        // given
        Member member = memberRepository.findById(2L).orElseThrow(() -> new NotFindDataException("회원없음"));

//        Contact contact = new Contact();
//        contact.setCompany("무슨회사");
//        contact.setDepartment("무슨부서");
//        contact.setContactName("아무개");
//        contact.setContactPhone("010-1234-5678");
//        contact.setContactEmail("asd@naver.com");
//        contact.setCompanyPhone("회사전화");
//        contact.setCompanyAddress("회사 주소");
//        contact.setMemo("암니암니아ㅣ");
//        contact.setMember(member);
//
//        contactRepository.save(contact);

//        List<Contact> contacts = contactRepository.findByMemberCode(member.getMemberCode());
//
//
//        System.out.println(contacts);

        //update
        Contact addressBookList = contactRepository.findById(125L).get();

        addressBookList.setContactLike('Y');


        // when

        // then
    }

    @Test
    @DisplayName("mail test")
    void test2() {

        Member member = memberRepository.findById(2L).orElseThrow(() -> new NotFindDataException("회원없음"));

        Email email = new Email();
        email.setMember(member);
        email.setMailDate("2023-8-23");
        email.setMailContent("아무말");
        email.setMailStatus('Y');
        email.setMailTitle("제에목");
        email.setMailLike('Y');


        emailAndMemberRepository.save(email);

//        List<Email> emails = emailAndMemberRepository.findByMemberCode(member.getMemberCode());
//
//        System.out.println("22222222222222222222222223"+emails.get(0).getMember().getMemberCode());


    }
}
