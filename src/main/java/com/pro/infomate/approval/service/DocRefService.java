package com.pro.infomate.approval.service;

import com.pro.infomate.approval.dto.DocumentDTO;
import com.pro.infomate.approval.dto.response.DocumentListResponse;
import com.pro.infomate.approval.entity.DocRef;
import com.pro.infomate.approval.repository.DocRefRepository;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocRefService {

  private final MemberRepository memberRepository;
  private final DocRefRepository docRefRepository;
  private final ModelMapper modelMapper;

  public void docRefSave(){



  }

  // 참조문서 5개 조회
  public List<DocumentListResponse> refDocList(int memberCode){
    Member member = memberRepository.findById(memberCode).orElseThrow();

    List<DocRef> refs = docRefRepository.findTop5ByMemberOrderByDocumentDesc(member);

    return refs.stream()
            .map(DocRef::getDocument)
            .map((element) -> modelMapper.map(element, DocumentListResponse.class))
            .collect(Collectors.toList());
  }

  // 페이징 참조문서 조회

}
