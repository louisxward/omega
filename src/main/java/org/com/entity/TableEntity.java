package org.com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.hibernate.envers.Audited;

import java.util.List;

@Entity
@Audited
public class TableEntity extends BaseEntity {
    public String name;
    @OneToMany(mappedBy = "table")
    public List<FieldEntity> fields;
}