package com.pro.infomate.email.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMailReference is a Querydsl query type for MailReference
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMailReference extends EntityPathBase<MailReference> {

    private static final long serialVersionUID = 297796456L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMailReference mailReference = new QMailReference("mailReference");

    public final QEmail mail;

    public final QEmail member;

    public final NumberPath<Integer> referenceCode = createNumber("referenceCode", Integer.class);

    public QMailReference(String variable) {
        this(MailReference.class, forVariable(variable), INITS);
    }

    public QMailReference(Path<? extends MailReference> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMailReference(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMailReference(PathMetadata metadata, PathInits inits) {
        this(MailReference.class, metadata, inits);
    }

    public QMailReference(Class<? extends MailReference> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mail = inits.isInitialized("mail") ? new QEmail(forProperty("mail"), inits.get("mail")) : null;
        this.member = inits.isInitialized("member") ? new QEmail(forProperty("member"), inits.get("member")) : null;
    }

}

