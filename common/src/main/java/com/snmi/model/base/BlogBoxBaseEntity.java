package com.snmi.model.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Objects;

/**
 * BlogBoxBaseEntity which contains sensitive information like ID and VERSION
 * Another entity can extend that BlogBoxBaseEntity if it want to receive unique fields like ID and VERSION
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@MappedSuperclass
public class BlogBoxBaseEntity implements Serializable {

    private static final long serialVersionUID = 935161223793063422L;

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (this.id == null) {
            this.id = id;
        }
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BlogBoxBaseEntity that = (BlogBoxBaseEntity) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}
