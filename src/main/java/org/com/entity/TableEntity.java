package org.com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.util.List;

@Entity
@Audited
@Getter
@Setter
public class TableEntity extends BaseEntity {
    private String name;
    @OneToMany(mappedBy = "table")
    private List<FieldEntity> fields;
}