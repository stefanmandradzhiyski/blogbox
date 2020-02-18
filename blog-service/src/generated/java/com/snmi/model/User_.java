package com.snmi.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.snmi.model.base.BlogBoxBaseAuditEntity_ {

	public static volatile ListAttribute<User, News> news;
	public static volatile SingularAttribute<User, String> firstName;
	public static volatile SingularAttribute<User, String> lastName;
	public static volatile SingularAttribute<User, String> password;
	public static volatile ListAttribute<User, Comment> comments;
	public static volatile ListAttribute<User, Favourite> favourites;
	public static volatile SingularAttribute<User, Boolean> display;
	public static volatile SetAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, Long> favouritesCount;
	public static volatile SingularAttribute<User, String> username;

	public static final String NEWS = "news";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String PASSWORD = "password";
	public static final String COMMENTS = "comments";
	public static final String FAVOURITES = "favourites";
	public static final String DISPLAY = "display";
	public static final String ROLES = "roles";
	public static final String FAVOURITES_COUNT = "favouritesCount";
	public static final String USERNAME = "username";

}

