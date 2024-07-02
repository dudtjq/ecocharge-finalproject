package com.example.demo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChargeInfo is a Querydsl query type for ChargeInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChargeInfo extends EntityPathBase<ChargeInfo> {

    private static final long serialVersionUID = -1864010669L;

    public static final QChargeInfo chargeInfo = new QChargeInfo("chargeInfo");

    public final StringPath addr = createString("addr");

    public final StringPath busiId = createString("busiId");

    public final StringPath busiNm = createString("busiNm");

    public final NumberPath<Long> chgerId = createNumber("chgerId", Long.class);

    public final NumberPath<Long> chgerType = createNumber("chgerType", Long.class);

    public final NumberPath<Double> lat = createNumber("lat", Double.class);

    public final StringPath limitDetail = createString("limitDetail");

    public final StringPath limitYn = createString("limitYn");

    public final NumberPath<Double> lng = createNumber("lng", Double.class);

    public final StringPath method = createString("method");

    public final StringPath note = createString("note");

    public final NumberPath<Long> output = createNumber("output", Long.class);

    public final StringPath parkingFree = createString("parkingFree");

    public final StringPath powerType = createString("powerType");

    public final NumberPath<Long> stat = createNumber("stat", Long.class);

    public final NumberPath<Long> statId = createNumber("statId", Long.class);

    public final StringPath statNm = createString("statNm");

    public final StringPath useTime = createString("useTime");

    public final NumberPath<Long> zcode = createNumber("zcode", Long.class);

    public QChargeInfo(String variable) {
        super(ChargeInfo.class, forVariable(variable));
    }

    public QChargeInfo(Path<? extends ChargeInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChargeInfo(PathMetadata metadata) {
        super(ChargeInfo.class, metadata);
    }

}

