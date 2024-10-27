package org.com.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;


@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity extends PanacheEntity {

    @ManyToOne
    private User modifiedBy;

    @PrePersist
    @PreUpdate
    private void onPersistOrUpdate() {
        // TODO: Set this.modifiedBy to the current user
    }
}