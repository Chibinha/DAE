package com.example.backend.services;

import com.example.backend.dtos.DTOConverter;
import com.example.backend.entities.PhysicalProduct;
import com.example.backend.dtos.PhysicalProductDTO;
import com.example.backend.ejbs.PhysicalProductBean;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/physicalProducts")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PhysicalProductService {
    @EJB
    private PhysicalProductBean physicalProductBean;
    private final DTOConverter dtoConverter = new DTOConverter();

    // CRUD
    // Create
    @POST
    @Path("/")
    public Response create(PhysicalProductDTO physicalProductDTO) throws MyEntityNotFoundException {
         long id = physicalProductBean.create(
                physicalProductDTO.getSerialNumber(),
                physicalProductDTO.getProductId()
        );
        if (id < 1) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Physical product with id [" + id + "] not created.").build();
        }
        return Response.status(Response.Status.CREATED).entity("Physical product with id [" + id + "] created.").build();
    }

    // Read
    @GET
    @Path("/")
    public List<PhysicalProductDTO> getAll() {
        return dtoConverter.physicalProductToDTOList(physicalProductBean.getAll());
    }

    // Find
    @GET
    @Path("/{id}")
    public PhysicalProductDTO find(@PathParam("id") long id) throws MyEntityNotFoundException {
        return dtoConverter.physicalProductToDTO(physicalProductBean.find(id));
    }

    // Update
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") long id, String serialNumber) throws MyEntityNotFoundException {
        physicalProductBean.update(id, serialNumber);

        PhysicalProduct physicalProduct = physicalProductBean.find(id);
        if (physicalProduct == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Physical product with id [" + id + "] not found.").build();
        }

        if (!physicalProduct.getSerialNumber().equals(serialNumber)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Physical product with id [" + id + "] not updated.").build();
        }
        return Response.status(Response.Status.OK).entity(dtoConverter.physicalProductToDTO(physicalProduct)).build();
    }

    // Delete
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) throws MyEntityNotFoundException {
        physicalProductBean.delete(id);

        if (physicalProductBean.exists(id)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Physical product with id [" + id + "] not deleted.").build();
        }
        return Response.status(Response.Status.OK).entity("Physical product with id [" + id + "] deleted.").build();
    }
}
