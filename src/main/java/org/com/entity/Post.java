package org.com.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Post extends PanacheEntityBase {
    @Id
    public Long id;
    public Long userId;
    public String title;
    public String body;
}