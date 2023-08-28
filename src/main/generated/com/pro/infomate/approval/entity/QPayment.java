package com.pro.infomate.approval.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayment extends EntityPathBase<Payment> {

    private static final long serialVersionUID = 907280665L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayment payment = new QPayment("payment");

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

    //inherited
    public final ListPath<DocumentFile, QDocumentFile> fileList;

    //inherited
    public final NumberPath<Long> id;

    // inherited
    public final com.pro.infomate.member.entity.QMember member;

    public final ListPath<PaymentList, QPaymentList> paymentList = this.<PaymentList, QPaymentList>createList("paymentList", PaymentList.class, QPaymentList.class, PathInits.DIRECT2);

    //inherited
    public final ListPath<DocRef, QDocRef> refList;

    //inherited
    public final StringPath title;

    public QPayment(String variable) {
        this(Payment.class, forVariable(variable), INITS);
    }

    public QPayment(Path<? extends Payment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPayment(PathMetadata metadata, PathInits inits) {
        this(Payment.class, metadata, inits);
    }

    public QPayment(Class<? extends Payment> type, PathMetadata metadata, PathInits inits) {
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

