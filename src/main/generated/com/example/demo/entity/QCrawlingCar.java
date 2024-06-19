package com.example.demo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCrawlingCar is a Querydsl query type for CrawlingCar
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCrawlingCar extends EntityPathBase<CrawlingCar> {

    private static final long serialVersionUID = 795188936L;

    public static final QCrawlingCar crawlingCar = new QCrawlingCar("crawlingCar");

    public final NumberPath<Integer> dataId = createNumber("dataId", Integer.class);

    public final StringPath imgUrl = createString("imgUrl");

    public final StringPath infoUrl = createString("infoUrl");

    public QCrawlingCar(String variable) {
        super(CrawlingCar.class, forVariable(variable));
    }

    public QCrawlingCar(Path<? extends CrawlingCar> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCrawlingCar(PathMetadata metadata) {
        super(CrawlingCar.class, metadata);
    }

}

