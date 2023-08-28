package com.pro.infomate.approval.repository;


import com.pro.infomate.approval.entity.Approval;
import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.approval.entity.QApproval;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.pro.infomate.approval.entity.QApproval.*;

@RequiredArgsConstructor
public class ApprovalRepositoryImpl implements ApprovalRepositoryCustom{

  private final JPAQueryFactory queryFactory;

}
