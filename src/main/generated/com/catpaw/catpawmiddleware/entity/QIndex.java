package com.catpaw.catpawmiddleware.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.catpaw.catpawmiddleware.domain.entity.Index;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIndex is a Querydsl query type for Index
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIndex extends EntityPathBase<Index> {

    private static final long serialVersionUID = -2067597662L;

    public static final QIndex index = new QIndex("index1");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QIndex(String variable) {
        super(Index.class, forVariable(variable));
    }

    public QIndex(Path<? extends Index> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIndex(PathMetadata metadata) {
        super(Index.class, metadata);
    }

}

