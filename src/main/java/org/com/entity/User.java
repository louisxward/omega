package org.com.entity;

import jakarta.persistence.Entity;
import org.hibernate.envers.Audited;


@Entity
@Audited
public class User extends BaseEntity {
    public String username;
    public String password;
}