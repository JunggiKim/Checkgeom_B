package io.dodn.springboot.storage.db.core;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExampleEntity is a Querydsl query type for ExampleEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExampleEntity extends EntityPathBase<ExampleEntity> {

    private static final long serialVersionUID = -120849539L;

    public static final QExampleEntity exampleEntity = new QExampleEntity("exampleEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath exampleColumn = createString("exampleColumn");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QExampleEntity(String variable) {
        super(ExampleEntity.class, forVariable(variable));
    }

    public QExampleEntity(Path<? extends ExampleEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExampleEntity(PathMetadata metadata) {
        super(ExampleEntity.class, metadata);
    }

}

