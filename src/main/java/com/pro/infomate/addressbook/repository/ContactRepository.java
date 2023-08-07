package com.pro.infomate.addressbook.repository;

import com.pro.infomate.addressbook.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
}
