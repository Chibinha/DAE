package com.example.backend.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import com.example.backend.exceptions.NotAuthorizedException;

import java.util.logging.Logger;

public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {
    private static final Logger logger =
        Logger.getLogger(NotAuthorizedException.class.getCanonicalName());
    @Override
    public Response toResponse(NotAuthorizedException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.UNAUTHORIZED)
        .entity(errorMsg)
        .build();
        }
}