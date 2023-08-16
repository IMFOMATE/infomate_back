package com.pro.infomate.approval.service;

import com.pro.infomate.approval.dto.*;
import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.approval.entity.Draft;
import com.pro.infomate.approval.entity.Payment;
import com.pro.infomate.approval.entity.Vacation;
import com.pro.infomate.approval.repository.DocumentRepository;
import com.pro.infomate.exception.NotFindDataException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentService {

  private final DocumentRepository<Document> documentRepository;

  private final ModelMapper modelMapper;

  //문서 세부
  public DocumentDTO findById(long DocumentId) {

    Document document = documentRepository.findById(DocumentId).orElseThrow(() -> new NotFindDataException("해당문서가 없습니다."));
    return mapDocumentToDTO(document);
  }

  private DocumentDTO mapDocumentToDTO(Document document) {
    if (document instanceof Vacation) {
      return modelMapper.map((Vacation) document, VacationDTO.class);
    } else if (document instanceof Payment) {
      return modelMapper.map((Payment) document, PaymentDTO.class);
    } else if (document instanceof Draft) {
      return modelMapper.map((Draft) document, DraftDTO.class);
      // 다른 문서 유형에 대한 처리 추가 가능
    } else {
      throw new IllegalArgumentException("지원하지 않는 문서 유형입니다.");
    }
  }



}




