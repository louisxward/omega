package org.com.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.com.entity.Post;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/posts")
@RegisterRestClient(baseUri = "https://jsonplaceholder.typicode.com")
public interface PostExternalClient {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Post> getAll();
}