package org.com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class UserCredential extends BaseEntity {
    @OneToOne
    public User user;
    public String passwordHash;
    public String salt;
}