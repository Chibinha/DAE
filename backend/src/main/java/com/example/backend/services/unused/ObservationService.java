package com.example.backend.services.unused;

import com.example.backend.dtos.DTOConverter;
import com.example.backend.dtos.InventoryItemDTO;
import com.example.backend.dtos.ObservationDTO;
import com.example.backend.ejbs.ObservationBean;
import com.example.backend.ejbs.OrderBean;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
@Path("/observations")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ObservationService {


    @EJB
    private ObservationBean observationBean;

    @POST
    @Path("/")
    public Response create(ObservationDTO dto) throws MyEntityNotFoundException {
        long id = observationBean.create(dto.getValue(), dto.getSensorId(), dto.getOrderId());
        if (id < 1) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Inventory Item with id [" + id + "] not created.").build();
        }
        return Response.status(Response.Status.CREATED).entity("Inventory Item with id [" + id + "] created.").build();
    }
}
