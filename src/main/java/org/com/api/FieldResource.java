package org.com.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.com.entity.FieldEntity;
import org.com.model.FieldUpdateRequest;
import org.com.service.FieldService;

import java.util.List;

@Path("/fields")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FieldResource {
    
    @Inject
    FieldService service;
    
    @GET
    public List<FieldEntity> getAllFields() {
        return FieldEntity.listAll();
    }
    
    @GET
    @Path("/{id}")
    public FieldEntity getFieldById(@PathParam("id") Long id) {
        return FieldEntity.findById(id);
    }
    
    @POST
    public Response createField(FieldEntity field) {
        FieldEntity createdField = service.createField(field);
        return Response.status(201).entity(createdField).build();
    }
    
    @PUT
    @Path("/{id}")
    public Response updateField(@PathParam("id") Long id, FieldEntity field) {
        FieldUpdateRequest request = new FieldUpdateRequest(id, field);
        service.queueUpdateRequest(request);
        return Response.accepted().entity("Request queued for processing.").build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteField(@PathParam("id") Long id) {
        boolean deleted = FieldEntity.deleteById(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(404).build();
        }
    }
}