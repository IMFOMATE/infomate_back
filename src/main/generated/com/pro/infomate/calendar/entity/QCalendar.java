package com.pro.infomate.calendar.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCalendar is a Querydsl query type for Calendar
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCalendar extends EntityPathBase<Calendar> {

    private static final long serialVersionUID = -1761653370L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCalendar calendar = new QCalendar("calendar");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final BooleanPath defaultCalendar = createBoolean("defaultCalendar");

    public final NumberPath<Integer> departmentCode = createNumber("departmentCode", Integer.class);

    public final ListPath<FavoriteCalendar, QFavoriteCalendar> favoriteCalendar = this.<FavoriteCalendar, QFavoriteCalendar>createList("favoriteCalendar", FavoriteCalendar.class, QFavoriteCalendar.class, PathInits.DIRECT2);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> indexNo = createNumber("indexNo", Integer.class);

    public final StringPath labelColor = createString("labelColor");

    public final com.pro.infomate.member.entity.QMember member;

    public final NumberPath<Integer> memberCode = createNumber("memberCode", Integer.class);

    public final StringPath name = createString("name");

    public final BooleanPath openStatus = createBoolean("openStatus");

    public final ListPath<Schedule, QSchedule> schedule = this.<Schedule, QSchedule>createList("schedule", Schedule.class, QSchedule.class, PathInits.DIRECT2);

    public QCalendar(String variable) {
        this(Calendar.class, forVariable(variable), INITS);
    }

    public QCalendar(Path<? extends Calendar> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCalendar(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCalendar(PathMetadata metadata, PathInits inits) {
        this(Calendar.class, metadata, inits);
    }

    public QCalendar(Class<? extends Calendar> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.pro.infomate.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

