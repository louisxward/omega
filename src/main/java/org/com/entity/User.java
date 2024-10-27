package org.com.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;


@Entity
@Audited
@Getter
@Setter
public class User extends BaseEntity {
    private String username;
    private String email;
}