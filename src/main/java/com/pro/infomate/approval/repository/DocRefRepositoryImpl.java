package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.dto.response.DocumentListResponse;
import com.pro.infomate.approval.dto.response.QDocumentListResponse;
import com.pro.infomate.approval.entity.*;
import com.pro.infomate.approval.entity.QDocRef;
import com.pro.infomate.approval.entity.QDocument;
import com.pro.infomate.approval.entity.QDraft;
import com.pro.infomate.approval.entity.QPayment;
import com.pro.infomate.approval.entity.QVacation;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.pro.infomate.approval.entity.QDocRef.*;
import static com.pro.infomate.approval.entity.QDocument.*;
import static com.pro.infomate.approval.entity.QDraft.*;
import static com.pro.infomate.approval.entity.QPayment.*;
import static com.pro.infomate.approval.entity.QVacation.*;

@RequiredArgsConstructor
public class DocRefRepositoryImpl implements DocRefRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<DocumentListResponse> refPagingList(int memberCode, Pageable pageable) {
    QDocRef docRef = QDocRef.docRef;
    QDocument document = QDocument.document;
    QDraft draft = QDraft.draft;  // Assuming you have a QEntity for Draft
    QPayment payment = QPayment.payment;  // Assuming you have a QEntity for Payment
    QVacation vacation = QVacation.vacation;

    List<DocumentListResponse> content = queryFactory.select(
                    new QDocumentListResponse(
                            document.id.as("id"),
                            document.title.as("title"),
                            document.documentStatus.as("documentStatus"),
                            document.emergency.as("emergency"),
                            document.createdDate.as("createdDate"),
                            document.documentKind.as("documentKind")
                    ))
            .from(docRef)
            .leftJoin(document).on(docRef.document.id.eq(document.id))
            .leftJoin(draft).on(document.id.eq(draft.id))
            .leftJoin(payment).on(document.id.eq(payment.id))
            .leftJoin(vacation).on(document.id.eq(vacation.id))
            .where(docRef.member.memberCode.eq(memberCode))
            .orderBy(document.createdDate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    JPAQuery<Long> countQuery = queryFactory
            .select(docRef.count())
            .from(docRef)
            .leftJoin(document).on(docRef.document.id.eq(document.id))
            .leftJoin(draft).on(document.id.eq(draft.id))
            .leftJoin(payment).on(document.id.eq(payment.id))
            .leftJoin(vacation).on(document.id.eq(vacation.id))
            .where(docRef.member.memberCode.eq(memberCode));

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }
}
