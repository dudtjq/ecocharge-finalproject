package com.example.demo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSubsidyCar is a Querydsl query type for SubsidyCar
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubsidyCar extends EntityPathBase<SubsidyCar> {

    private static final long serialVersionUID = -47293702L;

    public static final QSubsidyCar subsidyCar = new QSubsidyCar("subsidyCar");

    public final StringPath battery = createString("battery");

    public final StringPath callNumber = createString("callNumber");

    public final StringPath carName = createString("carName");

    public final StringPath company = createString("company");

    public final StringPath country = createString("country");

    public final StringPath fullChargeRange = createString("fullChargeRange");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath imgUrl = createString("imgUrl");

    public final StringPath ridingCapacity = createString("ridingCapacity");

    public final StringPath subsidy = createString("subsidy");

    public final StringPath topSpeed = createString("topSpeed");

    public QSubsidyCar(String variable) {
        super(SubsidyCar.class, forVariable(variable));
    }

    public QSubsidyCar(Path<? extends SubsidyCar> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubsidyCar(PathMetadata metadata) {
        super(SubsidyCar.class, metadata);
    }

}

