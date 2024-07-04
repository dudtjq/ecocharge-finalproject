package com.example.demo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCharger is a Querydsl query type for Charger
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCharger extends EntityPathBase<Charger> {

    private static final long serialVersionUID = 1063623437L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCharger charger = new QCharger("charger");

    public final StringPath bnm = createString("bnm");

    public final StringPath busiNm = createString("busiNm");

    public final QChargeSpot chargeInfo;

    public final StringPath chargerId = createString("chargerId");

    public final StringPath chgerType = createString("chgerType");

    public final StringPath id = createString("id");

    public final StringPath powerType = createString("powerType");

    public QCharger(String variable) {
        this(Charger.class, forVariable(variable), INITS);
    }

    public QCharger(Path<? extends Charger> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCharger(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCharger(PathMetadata metadata, PathInits inits) {
        this(Charger.class, metadata, inits);
    }

    public QCharger(Class<? extends Charger> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chargeInfo = inits.isInitialized("chargeInfo") ? new QChargeSpot(forProperty("chargeInfo")) : null;
    }

}

