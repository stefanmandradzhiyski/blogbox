package com.snmi.model;

import com.snmi.enums.Rating;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Comment.class)
public abstract class Comment_ extends com.snmi.model.base.BlogBoxBaseAuditEntity_ {

	public static volatile SingularAttribute<Comment, News> news;
	public static volatile SingularAttribute<Comment, Rating> rating;
	public static volatile SingularAttribute<Comment, String> fullComment;
	public static volatile SingularAttribute<Comment, User> user;

	public static final String NEWS = "news";
	public static final String RATING = "rating";
	public static final String FULL_COMMENT = "fullComment";
	public static final String USER = "user";

}

