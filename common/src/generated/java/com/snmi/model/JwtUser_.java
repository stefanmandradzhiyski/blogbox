package com.snmi.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(JwtUser.class)
public abstract class JwtUser_ extends com.snmi.model.base.BlogBoxBaseEntity_ {

	public static volatile SingularAttribute<JwtUser, String> password;
	public static volatile SetAttribute<JwtUser, Role> roles;
	public static volatile SingularAttribute<JwtUser, Boolean> display;
	public static volatile SingularAttribute<JwtUser, String> username;

	public static final String PASSWORD = "password";
	public static final String ROLES = "roles";
	public static final String DISPLAY = "display";
	public static final String USERNAME = "username";

}

