package com.example.demo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChargeSpot is a Querydsl query type for ChargeSpot
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChargeSpot extends EntityPathBase<ChargeSpot> {

    private static final long serialVersionUID = -1863710553L;

    public static final QChargeSpot chargeSpot = new QChargeSpot("chargeSpot");

    public final StringPath addr = createString("addr");

    public final StringPath facilityBig = createString("facilityBig");

    public final StringPath facilitySmall = createString("facilitySmall");

    public final StringPath latLng = createString("latLng");

    public final StringPath limitYn = createString("limitYn");

    public final StringPath statId = createString("statId");

    public final StringPath statNm = createString("statNm");

    public QChargeSpot(String variable) {
        super(ChargeSpot.class, forVariable(variable));
    }

    public QChargeSpot(Path<? extends ChargeSpot> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChargeSpot(PathMetadata metadata) {
        super(ChargeSpot.class, metadata);
    }

}

