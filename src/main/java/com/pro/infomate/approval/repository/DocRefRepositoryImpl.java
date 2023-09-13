package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.dto.response.DocumentListResponse;
import com.pro.infomate.approval.dto.response.QDocumentListResponse;

import com.pro.infomate.approval.entity.DocumentStatus;
import com.pro.infomate.member.entity.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.pro.infomate.approval.entity.QDocRef.*;
import static com.pro.infomate.approval.entity.QDocument.*;
import static com.pro.infomate.approval.entity.QDraft.*;
import static com.pro.infomate.approval.entity.QPayment.*;
import static com.pro.infomate.approval.entity.QVacation.*;
import static com.pro.infomate.member.entity.QMember.*;

@RequiredArgsConstructor
public class DocRefRepositoryImpl implements DocRefRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<DocumentListResponse> refPagingList(String status, int memberCode, Pageable pageable) {

    List<DocumentListResponse> content = queryFactory.select(
                    new QDocumentListResponse(
                            document.id.as("id"),
                            document.title.as("title"),
                            document.documentStatus.as("documentStatus"),
                            document.emergency.as("emergency"),
                            document.createdDate.as("createdDate"),
                            document.documentKind.as("documentKind"),
                            document.member.memberName.as("auth")
                    ))
            .from(docRef)
            .leftJoin(document).on(docRef.document.id.eq(document.id))
            .join(member).on(document.member.memberCode.eq(member.memberCode))
            .leftJoin(draft).on(document.id.eq(draft.id))
            .leftJoin(payment).on(document.id.eq(payment.id))
            .leftJoin(vacation).on(document.id.eq(vacation.id))
            .where(
                    memberCodeEq(memberCode),
                    documentStatus(status)
            )
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
            .where(
                    memberCodeEq(memberCode),
                    documentStatus(status)
            );

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }


  private BooleanExpression documentStatus(String status) {
    if ("null".equals(status) || !StringUtils.hasText(status)) {
      return document.documentStatus.ne(DocumentStatus.TEMPORARY);
    } else {
      return document.documentStatus.ne(DocumentStatus.TEMPORARY)
              .and(document.documentStatus.eq(DocumentStatus.valueOf(status)));
    }
  }

  private BooleanExpression memberCodeEq(Integer memberCode) {
    return memberCode != null ? docRef.member.memberCode.eq(memberCode) : null;
  }

}
