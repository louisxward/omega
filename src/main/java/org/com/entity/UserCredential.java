package org.com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;


@Entity
@Audited
@Getter
@Setter
public class UserCredential extends BaseEntity {
    @OneToOne
    private User user;
    private String passwordHash;
    private String salt;
}