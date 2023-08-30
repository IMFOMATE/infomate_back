package com.pro.infomate.approval.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDraft is a Querydsl query type for Draft
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDraft extends EntityPathBase<Draft> {

    private static final long serialVersionUID = -1100156556L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDraft draft = new QDraft("draft");

    public final QDocument _super;

    //inherited
    public final ListPath<Approval, QApproval> approvalList;

    public final StringPath coDept = createString("coDept");

    //inherited
    public final StringPath content;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate;

    //inherited
    public final StringPath documentKind;

    //inherited
    public final EnumPath<DocumentStatus> documentStatus;

    //inherited
    public final StringPath emergency;

    //inherited
    public final ListPath<DocumentFile, QDocumentFile> fileList;

    //inherited
    public final NumberPath<Long> id;

    // inherited
    public final com.pro.infomate.member.entity.QMember member;

    //inherited
    public final ListPath<DocRef, QDocRef> refList;

    //inherited
    public final StringPath title;

    public QDraft(String variable) {
        this(Draft.class, forVariable(variable), INITS);
    }

    public QDraft(Path<? extends Draft> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDraft(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDraft(PathMetadata metadata, PathInits inits) {
        this(Draft.class, metadata, inits);
    }

    public QDraft(Class<? extends Draft> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDocument(type, metadata, inits);
        this.approvalList = _super.approvalList;
        this.content = _super.content;
        this.createdDate = _super.createdDate;
        this.documentKind = _super.documentKind;
        this.documentStatus = _super.documentStatus;
        this.emergency = _super.emergency;
        this.fileList = _super.fileList;
        this.id = _super.id;
        this.member = _super.member;
        this.refList = _super.refList;
        this.title = _super.title;
    }

}

