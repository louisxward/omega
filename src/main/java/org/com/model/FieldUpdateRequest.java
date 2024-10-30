package org.com.model;


import org.com.entity.AppField;

public record FieldUpdateRequest(Long id, AppField field) {
}