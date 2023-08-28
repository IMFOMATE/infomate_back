package com.pro.infomate.addressbook.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContact is a Querydsl query type for Contact
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContact extends EntityPathBase<Contact> {

    private static final long serialVersionUID = 319356435L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContact contact = new QContact("contact");

    public final StringPath company = createString("company");

    public final StringPath companyAddress = createString("companyAddress");

    public final StringPath companyPhone = createString("companyPhone");

    public final NumberPath<Integer> contactCode = createNumber("contactCode", Integer.class);

    public final StringPath contactEmail = createString("contactEmail");

    public final StringPath contactName = createString("contactName");

    public final StringPath contactPhone = createString("contactPhone");

    public final StringPath department = createString("department");

    public final com.pro.infomate.member.entity.QMember member;

    public final StringPath memo = createString("memo");

    public QContact(String variable) {
        this(Contact.class, forVariable(variable), INITS);
    }

    public QContact(Path<? extends Contact> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContact(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContact(PathMetadata metadata, PathInits inits) {
        this(Contact.class, metadata, inits);
    }

    public QContact(Class<? extends Contact> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.pro.infomate.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

