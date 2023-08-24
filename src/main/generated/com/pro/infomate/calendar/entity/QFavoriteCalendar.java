package com.pro.infomate.calendar.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavoriteCalendar is a Querydsl query type for FavoriteCalendar
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavoriteCalendar extends EntityPathBase<FavoriteCalendar> {

    private static final long serialVersionUID = -1138534590L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavoriteCalendar favoriteCalendar = new QFavoriteCalendar("favoriteCalendar");

    public final EnumPath<com.pro.infomate.calendar.dto.ApprovalStatus> approvalStatus = createEnum("approvalStatus", com.pro.infomate.calendar.dto.ApprovalStatus.class);

    public final QCalendar calendar;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath labelColor = createString("labelColor");

    public final com.pro.infomate.member.entity.QMember member;

    public final NumberPath<Integer> memberCode = createNumber("memberCode", Integer.class);

    public final NumberPath<Integer> refCalendar = createNumber("refCalendar", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> requestDate = createDateTime("requestDate", java.time.LocalDateTime.class);

    public QFavoriteCalendar(String variable) {
        this(FavoriteCalendar.class, forVariable(variable), INITS);
    }

    public QFavoriteCalendar(Path<? extends FavoriteCalendar> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavoriteCalendar(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavoriteCalendar(PathMetadata metadata, PathInits inits) {
        this(FavoriteCalendar.class, metadata, inits);
    }

    public QFavoriteCalendar(Class<? extends FavoriteCalendar> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.calendar = inits.isInitialized("calendar") ? new QCalendar(forProperty("calendar"), inits.get("calendar")) : null;
        this.member = inits.isInitialized("member") ? new com.pro.infomate.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

