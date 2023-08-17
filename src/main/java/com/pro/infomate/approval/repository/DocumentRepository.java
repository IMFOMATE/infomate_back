package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentRepository<T extends Document> extends JpaRepository<T, Long> {

  //1. 결제할 문서(3), 작성한문서(5), 참조문서(5) 개씩 보여주기(최신순으로만)

  //2. 작성한 문서 orderBy -> 최신순, 오래된 순/ where -> 결재 상태별   

  //3. 임시저장상태 orderBy -> 최신순, 오래된 순

  //4. 문서 저장

  //5. 문서 세부 내용 조회



  @Query("select d from Document d where d.documentKind in (:type)")
  List<Document> findByDocuments(@Param("type") String type);

}
