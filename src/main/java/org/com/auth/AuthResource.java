package org.com.auth;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.com.model.AuthRequest;
import org.com.service.AuthService;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    @Inject
    AuthService authService;
    
    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(AuthRequest authRequest) {
        var token = authService.generateToken(authRequest);
        return token.isPresent() ? Response.ok(token).build() : Response.status(Response.Status.UNAUTHORIZED).build();
    }
    
    @POST
    @Path("/register")
    public Response register(AuthRequest authRequest) {
        return authService.register(authRequest) ? Response.status(Response.Status.CREATED).build() : Response.serverError().build();
    }
}