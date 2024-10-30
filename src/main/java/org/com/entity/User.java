package org.com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;


@Entity
@Table(name = "AppUser")
@Audited
@Getter
@Setter
public class User extends BaseEntity {
    private String username;
    private String email;
}