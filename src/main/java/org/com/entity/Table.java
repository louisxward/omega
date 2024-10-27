package org.com.entity;

import jakarta.persistence.Entity;
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
    private List<Field> fields;
}