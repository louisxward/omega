package org.com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class FieldEntity extends BaseEntity {
    public String valueString;
    @ManyToOne
    @JoinColumn(name = "table_id")
    public TableEntity table;
}