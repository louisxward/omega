package org.com.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.com.entity.Post;

import java.util.List;

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {
    
    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    public Response count() {
        return Response.status(Response.Status.OK.getStatusCode())
            .entity(Post.count())
            .build();
    }
    
    @GET
    @Path("/{id}")
    public Response get(String id) {
        var opt = Post.findByIdOptional(id);
        return Response.status(opt.isPresent() ? Response.Status.OK.getStatusCode() : Response.Status.NOT_FOUND.getStatusCode())
            .entity(opt.orElse(null))
            .build();
    }
    
    @GET
    @Path("/userId/{id}")
    public List<Post> getByUserId(String id) {
        return Post.list("userId", id);
    }
    
    @GET
    public List<Post> getAll() {
        return Post.listAll();
    }

//    @GET
//    public List<Post> getSearch(Long id, Long userId, String title) {
//        return Post.list("id = :id or userId = :userId or title = :title",
//            Parameters.with("id", id).and("userId", userId).and("title", title));
//    }

}