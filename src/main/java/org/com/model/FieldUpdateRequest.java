package org.com.model;

import lombok.Getter;
import lombok.Setter;
import org.com.entity.Field;

@Getter
@Setter
public class FieldUpdateRequest {
    private Long id;
    private Field field;

    public FieldUpdateRequest() {} // Default constructor for Jackson

    public FieldUpdateRequest(Long id, Field field) {
        this.id = id;
        this.field = field;
    }
}
