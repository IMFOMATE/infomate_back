package com.pro.infomate.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRank is a Querydsl query type for Rank
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRank extends EntityPathBase<Rank> {

    private static final long serialVersionUID = -993005808L;

    public static final QRank rank = new QRank("rank");

    public final NumberPath<Long> rankCode = createNumber("rankCode", Long.class);

    public final StringPath rankName = createString("rankName");

    public final NumberPath<Integer> rankPlace = createNumber("rankPlace", Integer.class);

    public QRank(String variable) {
        super(Rank.class, forVariable(variable));
    }

    public QRank(Path<? extends Rank> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRank(PathMetadata metadata) {
        super(Rank.class, metadata);
    }

}

