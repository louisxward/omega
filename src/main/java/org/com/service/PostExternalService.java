package org.com.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.com.entity.Post;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/posts")
@RegisterRestClient(baseUri = "https://jsonplaceholder.typicode.com")//configKey = "post-api"
public interface PostExternalService {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Post> getAll();
}