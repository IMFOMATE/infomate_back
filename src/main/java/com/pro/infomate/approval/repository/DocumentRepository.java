package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.print.Doc;
import java.util.List;

public interface DocumentRepository<T extends Document> extends JpaRepository<T, Long> {

//    List<Document> findByDocumentKindEquals(String kind);

  //1. 결제할 문서(3), 작성한문서(5), 참조문서(5) 개씩 보여주기
  
  //2. 작성한 문서 최신순 또는 결제 상태별로 조회 
  
  //3. 임시저장상태의 문서 최신순으로 조회


}
