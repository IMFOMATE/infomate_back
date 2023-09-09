package com.pro.infomate.email.repository;

import com.pro.infomate.email.entity.MailReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MailReferenceRepository extends JpaRepository<MailReference, Integer> {

}
