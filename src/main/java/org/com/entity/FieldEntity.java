package org.com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Getter
@Setter
public class FieldEntity extends BaseEntity {
    private String valueString;
    @ManyToOne
    @JoinColumn(name = "table_id")
    private TableEntity table;
}