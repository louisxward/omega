package org.com.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.com.exception.ValidationException;

import java.util.HashMap;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    
    @Override
    public Response toResponse(ValidationException exception) {
        var errorResponse = new HashMap<String, String>();
        errorResponse.put(exception.field, exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
    }
}