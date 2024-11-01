package org.com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "AppUser")
@Audited
public class User extends BaseEntity {
    public String username;
    public String email;
}