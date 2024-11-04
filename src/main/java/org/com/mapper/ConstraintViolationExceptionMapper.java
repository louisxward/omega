package org.com.mapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        var violations = exception.getConstraintViolations();
        var errorResponse = new HashMap<String, String>();
        for (ConstraintViolation<?> violation : violations) {
            var path = violation.getPropertyPath();
            var nodes = path.iterator();
            String property = null;
            while (nodes.hasNext()) {
                var node = nodes.next();
                if (node.getKind() == ElementKind.PROPERTY) {
                    property = node.getName();
                    break;
                }
            }
            errorResponse.put(null != property ? property : path.toString(), violation.getMessage());
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
    }
}