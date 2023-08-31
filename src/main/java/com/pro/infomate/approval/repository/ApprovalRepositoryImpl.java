package com.pro.infomate.approval.repository;


import com.pro.infomate.approval.entity.Approval;
import com.pro.infomate.approval.entity.Document;
<<<<<<< HEAD
//import com.pro.infomate.approval.entity.QApproval;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

//import static com.pro.infomate.approval.entity.QApproval.*;
=======

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


>>>>>>> 9b43f91e0443c7197b6c7438be37b47d19d65f14

@RequiredArgsConstructor
public class ApprovalRepositoryImpl implements ApprovalRepositoryCustom{

  private final JPAQueryFactory queryFactory;

}
