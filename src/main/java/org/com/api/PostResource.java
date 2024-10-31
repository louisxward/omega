package org.com.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.com.entity.Post;
import org.com.service.PostExternalService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {
    
    @RestClient
    PostExternalService client;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> getAllFields() {
        return client.getAll();
    }
    
}