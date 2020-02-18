package com.snmi.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BlogBoxBaseStatistic.class)
public abstract class BlogBoxBaseStatistic_ {

	public static volatile SingularAttribute<BlogBoxBaseStatistic, News> news;
	public static volatile SingularAttribute<BlogBoxBaseStatistic, Long> id;
	public static volatile SingularAttribute<BlogBoxBaseStatistic, User> user;

	public static final String NEWS = "news";
	public static final String ID = "id";
	public static final String USER = "user";

}

