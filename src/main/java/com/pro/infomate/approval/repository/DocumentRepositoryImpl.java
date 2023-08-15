package com.pro.infomate.approval.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DocumentRepositoryImpl implements DocumentRepositoryCustom {

  private final JPAQueryFactory queryFactory;



}
