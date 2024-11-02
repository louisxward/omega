package org.com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "AppUser", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
@Audited
public class User extends BaseEntity {
    public String username;
    public String email;
}