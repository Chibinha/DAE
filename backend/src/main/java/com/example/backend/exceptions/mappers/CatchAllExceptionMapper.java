package com.example.backend.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import com.example.backend.exceptions.CatchAllException;

import java.util.logging.Logger;

@Provider
public class CatchAllExceptionMapper implements ExceptionMapper<CatchAllException> {
    private static final Logger logger =
            Logger.getLogger(CatchAllExceptionMapper.class.getCanonicalName());
    @Override
    public Response toResponse(CatchAllException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorMsg)
                .build();
    }
}