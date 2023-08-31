package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.dto.response.DocumentListResponse;
import com.pro.infomate.approval.dto.response.QDocumentListResponse;
import com.pro.infomate.approval.entity.*;

import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static com.pro.infomate.approval.entity.QApproval.approval;
import static com.pro.infomate.approval.entity.QDocument.*;
import static com.pro.infomate.department.entity.QDepartment.department;
import static com.pro.infomate.member.entity.QMember.member;


@RequiredArgsConstructor
public class DocumentRepositoryImpl implements DocumentRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  //   본인 부서 중 완료된 문서 리스트
  @Override
  public Page<DocumentListResponse> findByDeptDoc(int memberCode, Pageable pageable) {

    SubQueryExpression<Integer> subQueryDeptCodes = JPAExpressions
            .select(department.deptCode)
            .from(member)
            .join(member.department, department)
            .where(member.memberCode.eq(2));

    List<DocumentListResponse> content = queryFactory.select(
                    new QDocumentListResponse(
                            document.id.as("id"),
                            document.title.as("title"),
                            document.documentStatus.as("documentStatus"),
                            document.emergency.as("emergency"),
                            document.createdDate.as("createdDate"),
                            document.documentKind.as("documentKind")
                    ))
            .from(document)
            .join(document.member, member)
            .join(member.department, department)
            .where(
                    document.documentStatus.eq(DocumentStatus.APPROVAL)
                            .and(department.deptCode.in(subQueryDeptCodes)))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    Long count = queryFactory.select(document.count())
            .from(document)
            .join(document.member, member)
            .join(member.department, department)
            .where(
                    document.documentStatus.eq(DocumentStatus.APPROVAL)
                            .and(department.deptCode.in(subQueryDeptCodes)))
            .fetchOne();

    return new PageImpl<>(content,pageable, count);
  }


  @Override
  public List<Document> findApprovalsDocument(int memberCode) {
    BooleanExpression approvalConditions = approval.member.memberCode.eq(memberCode)
            .and(approval.approvalDate.isNull());

    return queryFactory
            .select(document)
            .from(approval)
            .join(approval.document, document)
            .where(approvalConditions)
            .fetch();
  }

  //결재대기
  public List<Document> findCredit(int memberCode) {
    List<Approval> approvals = queryFactory
            .selectFrom(approval)
            .leftJoin(approval.member)
            .leftJoin(approval.document, document).fetchJoin()
            .where(approval.approvalDate.isNull()
                    .and(approval.member.memberCode.eq(memberCode)))
            .orderBy(approval.document.createdDate.desc())
            .limit(5)
            .fetch();

    return approvals.stream()
            .map(Approval::getDocument)
            .collect(Collectors.toList());
  }

  //페이징 결재대기
  public Page<Document> findCreditWithPaging(int memberCode, Pageable pageable) {
    List<Approval> approvals = queryFactory
            .selectFrom(approval)
            .leftJoin(approval.member)
            .leftJoin(approval.document, document).fetchJoin()
            .where(approval.approvalDate.isNull()
                    .and(approval.member.memberCode.eq(memberCode)))
//            .orderBy(approval.document.createdDate.desc())
            .fetch();

    List<Document> documents = approvals.stream()
            .map(Approval::getDocument)
            .collect(Collectors.toList());

    return new PageImpl<>(documents, pageable, documents.size());


  }


}