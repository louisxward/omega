package org.com.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.com.entity.Post;

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"standard"})
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
    public Response get(@PathParam("id") Long id) {
        var opt = Post.findByIdOptional(id);
        return Response.status(opt.isPresent() ? Response.Status.FOUND.getStatusCode() : Response.Status.NOT_FOUND.getStatusCode())
            .entity(opt.orElse(null))
            .build();
    }
    
    @GET
    @Path("/userId/{id}")
    public Response getByUserId(@PathParam("id") Long id) {
        var list = Post.list("userId", id);
        return Response.status(!list.isEmpty() ? Response.Status.OK.getStatusCode() : Response.Status.NO_CONTENT.getStatusCode())
            .entity(list)
            .build();
    }
    
    @GET
    public Response getAll() {
        var list = Post.listAll();
        return Response.status(!list.isEmpty() ? Response.Status.OK.getStatusCode() : Response.Status.NO_CONTENT.getStatusCode())
            .entity(list)
            .build();
    }

//    @GET
//    public List<Post> getSearch(Long id, Long userId, String title) {
//        return Post.list("id = :id or userId = :userId or title = :title",
//            Parameters.with("id", id).and("userId", userId).and("title", title));
//    }

}