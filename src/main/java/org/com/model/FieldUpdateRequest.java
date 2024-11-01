package org.com.model;

import org.com.entity.FieldEntity;

public record FieldUpdateRequest(Long id, FieldEntity field) {}