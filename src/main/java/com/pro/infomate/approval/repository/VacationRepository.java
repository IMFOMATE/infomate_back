package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.approval.entity.Vacation;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Long> {



}
