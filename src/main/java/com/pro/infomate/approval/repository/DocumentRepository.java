package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository<T extends Document> extends JpaRepository<T, Long> {

  //1. 결제할 문서(3), 작성한문서(5), 참조문서(5) 개씩 보여주기(최신순으로만)

  //2. 작성한 문서 orderBy -> 최신순, 오래된 순/ where -> 결재 상태별
  //3. 상태별 리스트
  Page<Document> findByDocumentStatus(String status, Pageable pageable);

  //4. 문서 저장

  //5. 부서문서 -> 기안 완료된 문서
  /*
  *
    select * from TBL_DOCUMENT TD
    left join TBL_MEMBER TM on TD.MEMBER_CODE = TM.MEMBER_CODE
    where TM.DEPT_CODE IN(
        select tbm.DEPT_CODE from TBL_MEMBER tbm where tbm.MEMBER_CODE=2
        )
    and TD.DOCUMENT_STATUS='WAITING';
  * */


  @Query("select d from Document d where d.documentKind in (:type)")
  List<Document> findByDocuments(@Param("type") String type);

}
