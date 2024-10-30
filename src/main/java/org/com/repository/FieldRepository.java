package org.com.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.com.entity.AppField;

@ApplicationScoped
public class FieldRepository implements PanacheRepository<AppField> {
}