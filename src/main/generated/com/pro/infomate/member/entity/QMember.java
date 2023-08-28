package com.pro.infomate.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -935328642L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final ListPath<com.pro.infomate.approval.entity.Approval, com.pro.infomate.approval.entity.QApproval> approvalList = this.<com.pro.infomate.approval.entity.Approval, com.pro.infomate.approval.entity.QApproval>createList("approvalList", com.pro.infomate.approval.entity.Approval.class, com.pro.infomate.approval.entity.QApproval.class, PathInits.DIRECT2);

    public final QDepartment department;

    public final ListPath<com.pro.infomate.approval.entity.Document, com.pro.infomate.approval.entity.QDocument> documentList = this.<com.pro.infomate.approval.entity.Document, com.pro.infomate.approval.entity.QDocument>createList("documentList", com.pro.infomate.approval.entity.Document.class, com.pro.infomate.approval.entity.QDocument.class, PathInits.DIRECT2);

    public final StringPath extensionNumber = createString("extensionNumber");

    public final DateTimePath<java.sql.Timestamp> hireDate = createDateTime("hireDate", java.sql.Timestamp.class);

    public final StringPath memberAddress = createString("memberAddress");

    public final NumberPath<Integer> memberCode = createNumber("memberCode", Integer.class);

    public final StringPath memberEmail = createString("memberEmail");

    public final StringPath memberId = createString("memberId");

    public final StringPath memberName = createString("memberName");

    public final StringPath memberNo = createString("memberNo");

    public final NumberPath<Integer> memberOff = createNumber("memberOff", Integer.class);

    public final StringPath memberPassword = createString("memberPassword");

    public final StringPath memberPhone = createString("memberPhone");

    public final StringPath memberPic = createString("memberPic");

    public final ListPath<com.pro.infomate.approval.entity.DocRef, com.pro.infomate.approval.entity.QDocRef> memberRefList = this.<com.pro.infomate.approval.entity.DocRef, com.pro.infomate.approval.entity.QDocRef>createList("memberRefList", com.pro.infomate.approval.entity.DocRef.class, com.pro.infomate.approval.entity.QDocRef.class, PathInits.DIRECT2);

    public final StringPath memberStatus = createString("memberStatus");

    public final QRank rank;

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.department = inits.isInitialized("department") ? new QDepartment(forProperty("department")) : null;
        this.rank = inits.isInitialized("rank") ? new QRank(forProperty("rank")) : null;
    }

}

