package com.pro.infomate.addressbook.repository;

import com.pro.infomate.addressbook.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Integer> {


    @Query("SELECT c FROM Contact c join c.member m where m.memberCode = :memberCode")
    List<Contact> findByContactTotleMemberCode(int memberCode);

    Page<Contact> findByMemberMemberCode(int i, Pageable paging);

    @Query("SELECT c FROM Contact c WHERE c.contactLike = 'Y'")
    Page<Contact> findByContactLike(char y, Pageable paging);

    @Query("SELECT c FROM Contact c WHERE c.contactLike = 'Y' AND c.member.memberCode = :memberCode")
    List<Contact> findByContactLikeTotal(Integer memberCode);

    @Query("SELECT c FROM Contact c WHERE c.contactName = :member")
    Contact findByContactName(Object member);

    @Query("SELECT c FROM Contact c WHERE c.contactLike = 'Y' AND c.member.memberCode = :memberCode")
    Page<Contact> findByContactLikeAndMemberMemberCode(Integer memberCode, Pageable paging);


//    Page<Contact> findByContactLike(char y, Pageable paging);


//    Page<Contact> findByMemberCodeContact(int i, Pageable paging);
}
