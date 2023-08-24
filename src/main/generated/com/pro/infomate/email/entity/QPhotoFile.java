package com.pro.infomate.email.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPhotoFile is a Querydsl query type for PhotoFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPhotoFile extends EntityPathBase<PhotoFile> {

    private static final long serialVersionUID = -1182821150L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPhotoFile photoFile = new QPhotoFile("photoFile");

    public final NumberPath<Integer> fileCode = createNumber("fileCode", Integer.class);

    public final StringPath fileDate = createString("fileDate");

    public final StringPath fileModificationName = createString("fileModificationName");

    public final StringPath fileSourceName = createString("fileSourceName");

    public final QEmail mail;

    public QPhotoFile(String variable) {
        this(PhotoFile.class, forVariable(variable), INITS);
    }

    public QPhotoFile(Path<? extends PhotoFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPhotoFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPhotoFile(PathMetadata metadata, PathInits inits) {
        this(PhotoFile.class, metadata, inits);
    }

    public QPhotoFile(Class<? extends PhotoFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mail = inits.isInitialized("mail") ? new QEmail(forProperty("mail"), inits.get("mail")) : null;
    }

}

