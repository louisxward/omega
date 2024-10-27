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
public class Table extends BaseEntity {
    private String name;
    @OneToMany
    private List<Field> fields;
}