package org.com.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.hibernate.envers.Audited;


@MappedSuperclass
@Audited
public abstract class BaseEntity extends PanacheEntity {

    @ManyToOne
    public User modifiedBy;

    @PrePersist
    @PreUpdate
    public void onPersistOrUpdate() {
        // TODO: Set this.modifiedBy to the current user
    }
}