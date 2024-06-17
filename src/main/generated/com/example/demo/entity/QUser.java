package com.example.demo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 133795676L;

    public static final QUser user = new QUser("user");

    public final StringPath email = createString("email");

    public final StringPath googleAccessToken = createString("googleAccessToken");

    public final StringPath id = createString("id");

    public final DateTimePath<java.time.LocalDateTime> joinDate = createDateTime("joinDate", java.time.LocalDateTime.class);

    public final StringPath kakaoAccessToken = createString("kakaoAccessToken");

    public final EnumPath<User.LoginMethod> loginMethod = createEnum("loginMethod", User.LoginMethod.class);

    public final StringPath naverAccessToken = createString("naverAccessToken");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath profileImg = createString("profileImg");

    public final StringPath refreshToken = createString("refreshToken");

    public final DateTimePath<java.util.Date> refreshTokenExpiryDate = createDateTime("refreshTokenExpiryDate", java.util.Date.class);

    public final StringPath userName = createString("userName");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

