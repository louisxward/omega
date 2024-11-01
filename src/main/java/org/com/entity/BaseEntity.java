package org.com.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@MappedSuperclass
public abstract class BaseEntity extends PanacheEntity {
    @OneToOne
    public User modifiedBy;
    
    @PrePersist
    @PreUpdate
    private void onPersistOrUpdate() {
        // TODO: Set this.modifiedBy to the current user
    }
}