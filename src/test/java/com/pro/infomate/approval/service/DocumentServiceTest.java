package com.pro.infomate.approval.service;

import com.pro.infomate.approval.dto.DocumentDTO;
import com.pro.infomate.approval.dto.VacationDTO;
import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.approval.entity.DocumentStatus;
import com.pro.infomate.approval.entity.Vacation;
import com.pro.infomate.approval.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DocumentServiceTest {


  @Autowired
  private DocumentService documentService;

  @Autowired private DocumentRepository<Vacation> documentRepository;

  @Test
  @DisplayName("insert")
  @Commit
  void insert() {



    VacationDTO vacationDTO = VacationDTO.builder()
            .title("휴가 승인바랍니다")
            .createdDate(LocalDateTime.now())
//            .documentStatus(DocumentStatus.WAITING)
            .fileList(new ArrayList<>())
            .approvalList(new ArrayList<>())
            .sort("연차")
            .startDate(LocalDateTime.of(2023,9,10,0,0))
            .endDate(LocalDateTime.of(2023,9,11,0,0))
            .reason("휴가쓸래...").build();

    VacationDTO vacation = documentService.vacationSave(2, vacationDTO);

    Vacation vacation1 = documentRepository.findById(vacation.getId()).orElseThrow();


  }

}