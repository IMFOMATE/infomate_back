package com.pro.infomate.email.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrash is a Querydsl query type for Trash
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTrash extends EntityPathBase<Trash> {

    private static final long serialVersionUID = 424637260L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrash trash = new QTrash("trash");

    public final QEmail mail;

    public final NumberPath<Integer> trashCode = createNumber("trashCode", Integer.class);

    public QTrash(String variable) {
        this(Trash.class, forVariable(variable), INITS);
    }

    public QTrash(Path<? extends Trash> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrash(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrash(PathMetadata metadata, PathInits inits) {
        this(Trash.class, metadata, inits);
    }

    public QTrash(Class<? extends Trash> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mail = inits.isInitialized("mail") ? new QEmail(forProperty("mail"), inits.get("mail")) : null;
    }

}

