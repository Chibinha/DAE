package com.example.backend.services;

import com.example.backend.dtos.NewPasswordDTO;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import com.example.backend.dtos.AuthDTO;
import com.example.backend.dtos.UserDTO;
import com.example.backend.ejbs.UserBean;
import com.example.backend.security.Authenticated;
import com.example.backend.security.TokenIssuer;

import java.security.Principal;

@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {
    @Inject
    private TokenIssuer issuer;
    @EJB
    private UserBean userBean;
    @Context
    private SecurityContext securityContext;

    @POST
    @Path("/login")
    public Response authenticate(@Valid AuthDTO auth) {
        if (userBean.canLogin(auth.getUsername(), auth.getPassword())) {
            String token = issuer.issue(auth.getUsername());
            return Response.ok(token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Authenticated
    @Path("/set-password")
    public Response setPassword(@Valid NewPasswordDTO passwordDTO) {
        Principal principal = securityContext.getUserPrincipal();
        if (userBean.updatePassword(principal.getName(), passwordDTO.getOldPassword(), passwordDTO.getNewPassword()))
            return Response.ok().build();
        else
            return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Authenticated
    @Path("/user")
    public Response getAuthenticatedUser() {
        var username = securityContext.getUserPrincipal().getName();
        var user = userBean.findOrFail(username);
        return Response.ok(UserDTO.from(user)).build();
    }
}
