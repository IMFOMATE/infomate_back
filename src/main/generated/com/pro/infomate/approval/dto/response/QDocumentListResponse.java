package com.pro.infomate.approval.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.pro.infomate.approval.dto.response.QDocumentListResponse is a Querydsl Projection type for DocumentListResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QDocumentListResponse extends ConstructorExpression<DocumentListResponse> {

    private static final long serialVersionUID = 731545754L;

    public QDocumentListResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<com.pro.infomate.approval.entity.DocumentStatus> documentStatus, com.querydsl.core.types.Expression<String> emergency, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdDate) {
        super(DocumentListResponse.class, new Class<?>[]{long.class, String.class, com.pro.infomate.approval.entity.DocumentStatus.class, String.class, java.time.LocalDateTime.class}, id, title, documentStatus, emergency, createdDate);
    }

}

