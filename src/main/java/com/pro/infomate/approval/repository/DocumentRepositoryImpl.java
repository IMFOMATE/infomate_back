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
import org.springframework.util.StringUtils;

import java.util.List;

import static com.pro.infomate.approval.entity.QApproval.*;
import static com.pro.infomate.approval.entity.QDocument.*;
import static com.pro.infomate.department.entity.QDepartment.department;
import static com.pro.infomate.member.entity.QMember.*;

@RequiredArgsConstructor
public class DocumentRepositoryImpl implements DocumentRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  // 본인 부서 중 완료된 문서 리스트
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
                            document.documentKind.as("documentKind"),
                            document.member.memberName.as("auth")
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



  //조건 페이징
  public Page<DocumentListResponse> findAllApproval(String status, int memberCode, Pageable pageable){
    List<DocumentListResponse> content= queryFactory.select(
                    new QDocumentListResponse(
                            document.id.as("id"),
                            document.title.as("title"),
                            document.documentStatus.as("documentStatus"),
                            document.emergency.as("emergency"),
                            document.createdDate.as("createdDate"),
                            document.documentKind.as("documentKind"),
                            document.member.memberName.as("auth")
                    ))
            .from(document)
            .join(document.member, member)
            .where(
                    memberCodeEq(memberCode),
                    documentStatus(status)
            )
            .orderBy(document.createdDate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    Long count = queryFactory.select(document.count())
            .from(document)
            .join(document.member, member)
            .where(
                    memberCodeEq(memberCode),
                    documentStatus(status)
            )
            .fetchOne();

    return new PageImpl<>(content,pageable, count);
  }

  private BooleanExpression documentStatus(String status) {
    return StringUtils.hasText(status)  ?  document.documentStatus.eq(DocumentStatus.valueOf(status)) : null;
  }

  private BooleanExpression memberCodeEq(Integer memberCode) {
    return memberCode != null ? member.memberCode.eq(memberCode) : null;
  }

}