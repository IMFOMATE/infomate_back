package com.pro.infomate.work.repository;

import com.pro.infomate.member.entity.Member;
import com.pro.infomate.work.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WorkRepository extends JpaRepository<Work, Integer> {


  public Optional<Work> findByYearMonthAndMember(LocalDate date, Member member);



}
