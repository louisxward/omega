package org.com.auth;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    public Response login(@NotNull @Valid AuthRequest authRequest) {
        return authService.verifyAndGenerateToken(authRequest);
    }
    
    @POST
    @Path("/register")
    @Produces(MediaType.TEXT_PLAIN)
    public Response register(@NotNull @Valid AuthRequest authRequest) {
        return authService.validateAndRegister(authRequest);
    }
}