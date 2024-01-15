package com.example.backend.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import com.example.backend.exceptions.MyEntityExistsException;

import java.util.logging.Logger;

@Provider
public class MyEntityExistsExceptionMapper
        implements ExceptionMapper<MyEntityExistsException> {
    private static final Logger logger =
            Logger.getLogger(MyEntityExistsException.class.getCanonicalName());
    @Override
    public Response toResponse(MyEntityExistsException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorMsg)
                .build();
    }
}