package com.pro.infomate.approval.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVacation is a Querydsl query type for Vacation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVacation extends EntityPathBase<Vacation> {

    private static final long serialVersionUID = -712624598L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVacation vacation = new QVacation("vacation");

    public final QDocument _super;

    //inherited
    public final ListPath<Approval, QApproval> approvalList;

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

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    //inherited
    public final ListPath<DocumentFile, QDocumentFile> fileList;

    //inherited
    public final NumberPath<Long> id;

    // inherited
    public final com.pro.infomate.member.entity.QMember member;

    public final StringPath reason = createString("reason");

    //inherited
    public final ListPath<DocRef, QDocRef> refList;

    public final StringPath sort = createString("sort");

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    //inherited
    public final StringPath title;

    public QVacation(String variable) {
        this(Vacation.class, forVariable(variable), INITS);
    }

    public QVacation(Path<? extends Vacation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVacation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVacation(PathMetadata metadata, PathInits inits) {
        this(Vacation.class, metadata, inits);
    }

    public QVacation(Class<? extends Vacation> type, PathMetadata metadata, PathInits inits) {
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

