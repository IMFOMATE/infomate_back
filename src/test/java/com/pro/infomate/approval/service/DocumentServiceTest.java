package com.pro.infomate.approval.service;

import com.pro.infomate.approval.dto.request.VacationRequest;
import com.pro.infomate.approval.dto.response.DocumentDetailResponse;
import com.pro.infomate.approval.dto.response.DocumentListResponse;
import com.pro.infomate.approval.dto.response.VacationResponse;
import com.pro.infomate.approval.entity.Vacation;
import com.pro.infomate.approval.repository.DocumentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class DocumentServiceTest {


  @Autowired
  private DocumentService documentService;

  @Autowired private DocumentRepository<Vacation> documentRepository;

//  @Test
//  @DisplayName("insert")
//  @Commit
//  void insert() {
//    List<Integer> ref = new ArrayList<>();
//    ref.add(2);
//    ref.add(43);
//    VacationRequest vacationDTO = VacationRequest.builder()
//            .refList(ref)
//            .title("휴가 승인바랍니다")
//            .approvalList(new ArrayList<>())
//            .sort("연차")
//            .startDate(LocalDateTime.of(2023,9,10,0,0))
//            .endDate(LocalDateTime.of(2023,9,11,0,0))
//            .reason("휴가쓸래...").build();
////
//    VacationResponse vacation = documentService.vacationSave(22, vacationDTO,null);
//    System.out.println("vacation = " + vacation);
//
//  }
  
  @Test
  @DisplayName("세부내용")
  void 세부내용() {
    DocumentDetailResponse result = documentService.findById(28);
  }

  
  @Test
  @DisplayName("문서상태에따른 페이징")
  void 문서상태페이지() {
    // Given
    PageRequest.of(0, 20);

    Page<DocumentListResponse> temporary = documentService.approvalList("TEMPORARY", 43, PageRequest.of(0, 20));
    System.out.println("temporary.getContent() = " + temporary.getContent());


  }
}