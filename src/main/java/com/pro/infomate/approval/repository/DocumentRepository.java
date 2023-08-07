package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.print.Doc;
import java.util.List;

public interface DocumentRepository<T extends Document> extends JpaRepository<T, Long> {

//    List<Document> findByDocumentKindEquals(String kind);

}
