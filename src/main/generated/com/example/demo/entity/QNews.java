package com.example.demo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNews is a Querydsl query type for News
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNews extends EntityPathBase<News> {

    private static final long serialVersionUID = 133574244L;

    public static final QNews news = new QNews("news");

    public final StringPath content = createString("content");

    public final StringPath imgUrl = createString("imgUrl");

    public final NumberPath<Integer> newsNo = createNumber("newsNo", Integer.class);

    public final StringPath regDate = createString("regDate");

    public final StringPath source = createString("source");

    public final StringPath title = createString("title");

    public QNews(String variable) {
        super(News.class, forVariable(variable));
    }

    public QNews(Path<? extends News> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNews(PathMetadata metadata) {
        super(News.class, metadata);
    }

}

