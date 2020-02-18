package com.snmi.model.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * BlogBoxBaseAuditEntity which contains information about the creation and update of specific entity
 * Extends the BlogBoxBaseEntity and receive its fields too
 * Another entity can extend that BlogBoxBaseAuditEntity if it want to receive all those kind of fields
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@MappedSuperclass
public class BlogBoxBaseAuditEntity extends BlogBoxBaseEntity {

    private static final long serialVersionUID = 3093769710228132881L;

    @Column(name = "created_at")
    @NotNull
    private Date createdAt;

    @Column(name = "updated_at")
    @NotNull
    private Date updatedAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        BlogBoxBaseAuditEntity that = (BlogBoxBaseAuditEntity) o;
        return Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), createdAt, updatedAt);
    }
}
