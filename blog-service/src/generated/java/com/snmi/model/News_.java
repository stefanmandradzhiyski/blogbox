package com.snmi.model;

import com.snmi.enums.Status;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(News.class)
public abstract class News_ extends com.snmi.model.base.BlogBoxBaseAuditEntity_ {

	public static volatile ListAttribute<News, Comment> comments;
	public static volatile ListAttribute<News, Favourite> favourites;
	public static volatile SingularAttribute<News, Boolean> display;
	public static volatile SingularAttribute<News, Long> viewsCount;
	public static volatile SingularAttribute<News, Long> dislikesCount;
	public static volatile SingularAttribute<News, Long> favouritesCount;
	public static volatile ListAttribute<News, Dislike> dislikes;
	public static volatile SingularAttribute<News, String> shortDescription;
	public static volatile SingularAttribute<News, Long> likesCount;
	public static volatile SingularAttribute<News, String> name;
	public static volatile SingularAttribute<News, String> mainInformation;
	public static volatile SingularAttribute<News, User> user;
	public static volatile ListAttribute<News, Like> likes;
	public static volatile SingularAttribute<News, Status> status;

	public static final String COMMENTS = "comments";
	public static final String FAVOURITES = "favourites";
	public static final String DISPLAY = "display";
	public static final String VIEWS_COUNT = "viewsCount";
	public static final String DISLIKES_COUNT = "dislikesCount";
	public static final String FAVOURITES_COUNT = "favouritesCount";
	public static final String DISLIKES = "dislikes";
	public static final String SHORT_DESCRIPTION = "shortDescription";
	public static final String LIKES_COUNT = "likesCount";
	public static final String NAME = "name";
	public static final String MAIN_INFORMATION = "mainInformation";
	public static final String USER = "user";
	public static final String LIKES = "likes";
	public static final String STATUS = "status";

}

