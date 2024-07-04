package com.example.demo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChargeSpot1 is a Querydsl query type for ChargeSpot1
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChargeSpot1 extends EntityPathBase<ChargeSpot1> {

    private static final long serialVersionUID = -1940452246L;

    public static final QChargeSpot1 chargeSpot1 = new QChargeSpot1("chargeSpot1");

    public final StringPath addr = createString("addr");

    public final StringPath facilityBig = createString("facilityBig");

    public final StringPath facilitySmall = createString("facilitySmall");

    public final StringPath latLng = createString("latLng");

    public final StringPath limitYn = createString("limitYn");

    public final StringPath statId = createString("statId");

    public final StringPath statNm = createString("statNm");

    public final NumberPath<Long> tableNum = createNumber("tableNum", Long.class);

    public QChargeSpot1(String variable) {
        super(ChargeSpot1.class, forVariable(variable));
    }

    public QChargeSpot1(Path<? extends ChargeSpot1> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChargeSpot1(PathMetadata metadata) {
        super(ChargeSpot1.class, metadata);
    }

}

