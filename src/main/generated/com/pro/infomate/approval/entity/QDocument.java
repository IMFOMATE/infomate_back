package com.pro.infomate.approval.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDocument is a Querydsl query type for Document
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDocument extends EntityPathBase<Document> {

    private static final long serialVersionUID = 1834935400L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDocument document = new QDocument("document");

    public final ListPath<Approval, QApproval> approvalList = this.<Approval, QApproval>createList("approvalList", Approval.class, QApproval.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final StringPath documentKind = createString("documentKind");

    public final EnumPath<DocumentStatus> documentStatus = createEnum("documentStatus", DocumentStatus.class);

    public final StringPath emergency = createString("emergency");

    public final ListPath<DocumentFile, QDocumentFile> fileList = this.<DocumentFile, QDocumentFile>createList("fileList", DocumentFile.class, QDocumentFile.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.pro.infomate.member.entity.QMember member;

    public final ListPath<DocRef, QDocRef> refList = this.<DocRef, QDocRef>createList("refList", DocRef.class, QDocRef.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QDocument(String variable) {
        this(Document.class, forVariable(variable), INITS);
    }

    public QDocument(Path<? extends Document> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDocument(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDocument(PathMetadata metadata, PathInits inits) {
        this(Document.class, metadata, inits);
    }

    public QDocument(Class<? extends Document> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.pro.infomate.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

