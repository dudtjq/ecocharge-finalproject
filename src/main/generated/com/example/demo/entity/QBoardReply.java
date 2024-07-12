package com.example.demo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardReply is a Querydsl query type for BoardReply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardReply extends EntityPathBase<BoardReply> {

    private static final long serialVersionUID = -1537374603L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardReply boardReply = new QBoardReply("boardReply");

    public final QBoard board;

    public final DateTimePath<java.time.LocalDateTime> replyDate = createDateTime("replyDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> replyNo = createNumber("replyNo", Long.class);

    public final StringPath replyText = createString("replyText");

    public final StringPath replyWriter = createString("replyWriter");

    public final DateTimePath<java.time.LocalDateTime> updateReplyDate = createDateTime("updateReplyDate", java.time.LocalDateTime.class);

    public final StringPath userId = createString("userId");

    public QBoardReply(String variable) {
        this(BoardReply.class, forVariable(variable), INITS);
    }

    public QBoardReply(Path<? extends BoardReply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardReply(PathMetadata metadata, PathInits inits) {
        this(BoardReply.class, metadata, inits);
    }

    public QBoardReply(Class<? extends BoardReply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

