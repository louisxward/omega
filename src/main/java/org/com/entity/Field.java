package org.com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;


@Entity
@Audited
@Getter
@Setter
public class Field extends BaseEntity {
    private String valueString;
    private Boolean valueBoolean;
    @ManyToOne
    private Table table;
}