package com.pro.infomate.approval.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDocRef is a Querydsl query type for DocRef
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDocRef extends EntityPathBase<DocRef> {

    private static final long serialVersionUID = 252154568L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDocRef docRef = new QDocRef("docRef");

    public final QDocument document;

    public final com.pro.infomate.member.entity.QMember member;

    public final NumberPath<Long> refCode = createNumber("refCode", Long.class);

    public QDocRef(String variable) {
        this(DocRef.class, forVariable(variable), INITS);
    }

    public QDocRef(Path<? extends DocRef> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDocRef(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDocRef(PathMetadata metadata, PathInits inits) {
        this(DocRef.class, metadata, inits);
    }

    public QDocRef(Class<? extends DocRef> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document"), inits.get("document")) : null;
        this.member = inits.isInitialized("member") ? new com.pro.infomate.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

