package com.pro.infomate.approval.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPaymentList is a Querydsl query type for PaymentList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentList extends EntityPathBase<PaymentList> {

    private static final long serialVersionUID = 464515415L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPaymentList paymentList = new QPaymentList("paymentList");

    public final QDocument document;

    public final NumberPath<Long> paymentCode = createNumber("paymentCode", Long.class);

    public final StringPath paymentContent = createString("paymentContent");

    public final DateTimePath<java.time.LocalDateTime> paymentDate = createDateTime("paymentDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> paymentPrice = createNumber("paymentPrice", Integer.class);

    public final StringPath paymentReason = createString("paymentReason");

    public final StringPath paymentSort = createString("paymentSort");

    public final StringPath remarks = createString("remarks");

    public QPaymentList(String variable) {
        this(PaymentList.class, forVariable(variable), INITS);
    }

    public QPaymentList(Path<? extends PaymentList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPaymentList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPaymentList(PathMetadata metadata, PathInits inits) {
        this(PaymentList.class, metadata, inits);
    }

    public QPaymentList(Class<? extends PaymentList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document"), inits.get("document")) : null;
    }

}

