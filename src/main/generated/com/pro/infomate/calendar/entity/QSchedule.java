package com.pro.infomate.calendar.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSchedule is a Querydsl query type for Schedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchedule extends EntityPathBase<Schedule> {

    private static final long serialVersionUID = 2013717727L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSchedule schedule = new QSchedule("schedule");

    public final StringPath address = createString("address");

    public final BooleanPath allDay = createBoolean("allDay");

    public final QCalendar calendar;

    public final StringPath content = createString("content");

    public final BooleanPath corpSchdl = createBoolean("corpSchdl");

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final BooleanPath important = createBoolean("important");

    public final ListPath<Participant, QParticipant> participantList = this.<Participant, QParticipant>createList("participantList", Participant.class, QParticipant.class, PathInits.DIRECT2);

    public final NumberPath<Integer> refCalendar = createNumber("refCalendar", Integer.class);

    public final BooleanPath repeat = createBoolean("repeat");

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public QSchedule(String variable) {
        this(Schedule.class, forVariable(variable), INITS);
    }

    public QSchedule(Path<? extends Schedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSchedule(PathMetadata metadata, PathInits inits) {
        this(Schedule.class, metadata, inits);
    }

    public QSchedule(Class<? extends Schedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.calendar = inits.isInitialized("calendar") ? new QCalendar(forProperty("calendar"), inits.get("calendar")) : null;
    }

}

