package com.pro.infomate.member.repository;

import com.pro.infomate.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistRepository extends JpaRepository<Member, Integer> {

}
