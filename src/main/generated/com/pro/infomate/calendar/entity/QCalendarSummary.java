package com.pro.infomate.calendar.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCalendarSummary is a Querydsl query type for CalendarSummary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCalendarSummary extends EntityPathBase<CalendarSummary> {

    private static final long serialVersionUID = 1794953024L;

    public static final QCalendarSummary calendarSummary = new QCalendarSummary("calendarSummary");

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final DatePath<java.time.LocalDate> day = createDate("day", java.time.LocalDate.class);

    public QCalendarSummary(String variable) {
        super(CalendarSummary.class, forVariable(variable));
    }

    public QCalendarSummary(Path<? extends CalendarSummary> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCalendarSummary(PathMetadata metadata) {
        super(CalendarSummary.class, metadata);
    }

}

