package com.snmi.model.base;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BlogBoxBaseAuditEntity.class)
public abstract class BlogBoxBaseAuditEntity_ extends com.snmi.model.base.BlogBoxBaseEntity_ {

	public static volatile SingularAttribute<BlogBoxBaseAuditEntity, Date> createdAt;
	public static volatile SingularAttribute<BlogBoxBaseAuditEntity, Date> updatedAt;

	public static final String CREATED_AT = "createdAt";
	public static final String UPDATED_AT = "updatedAt";

}

