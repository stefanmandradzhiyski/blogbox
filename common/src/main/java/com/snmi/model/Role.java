package com.snmi.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.snmi.model.base.BlogBoxBaseEntity;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;

/**
 * Role entity represents the user role in the system and role table in the database
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Entity
@DynamicUpdate
@Table(name = "roles")
public class Role extends BlogBoxBaseEntity implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    @NotNull
    @Size(min = 3, max = 15, message = "Role name length must be between 3 and 15")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

}
