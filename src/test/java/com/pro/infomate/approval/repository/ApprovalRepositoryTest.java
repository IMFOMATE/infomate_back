package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.entity.Approval;
import com.pro.infomate.approval.entity.Document;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ApprovalRepositoryTest {

  @Autowired private ApprovalRepository approvalRepository;
  @Autowired private DocumentRepository<Document> documentRepository;



  @Test
  @DisplayName("top1")
  void top1() {
    int memberCode =2;

    List<Document> result = documentRepository.findCredit(memberCode);

    result.forEach(d->{
      System.out.println("d. = " + d.getId());
      System.out.println("d. = " + d.getCreatedDate());
    });

  }

  @Test
  @DisplayName("top1")
  void top1Test(){


  }

}