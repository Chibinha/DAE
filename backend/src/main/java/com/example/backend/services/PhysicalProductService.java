package com.example.backend.services;

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

    private PhysicalProductDTO toDTO(PhysicalProduct physicalProduct) {
        return new PhysicalProductDTO(
                physicalProduct.getId(),
                physicalProduct.getProduct().getId(),
                physicalProduct.getProduct().getName(),
                physicalProduct.getSerialNumber(),
                physicalProduct.getStockTimestamp()
        );
    }

    private List<PhysicalProductDTO> toDTOs(List<PhysicalProduct> physicalProducts) {
        return physicalProducts.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // CRUD

    // Create
    @POST
    @Path("/")
    public Response create(PhysicalProductDTO physicalProductDTO) throws MyEntityNotFoundException {
         long id = physicalProductBean.create(
                physicalProductDTO.getSerialNumber(),
                physicalProductDTO.getProductId()
        );

        PhysicalProduct newPhysicalProduct = physicalProductBean.find(id);
        if (newPhysicalProduct == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Physical product with id: " + id + "not created.").build();
        }
        return Response.status(Response.Status.CREATED).entity(toDTO(newPhysicalProduct)).build();
    }

    // Read
    @GET
    @Path("/")
    public List<PhysicalProductDTO> getAll() {
        return toDTOs(physicalProductBean.getAll());
    }

    // Find
    @GET
    @Path("/{id}")
    public PhysicalProductDTO find(@PathParam("id") Long id) throws MyEntityNotFoundException {
        return toDTO(physicalProductBean.find(id));
    }

    // Update
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, String serialNumber) throws MyEntityNotFoundException {
        physicalProductBean.update(id, serialNumber);

        PhysicalProduct physicalProduct = physicalProductBean.find(id);
        if (physicalProduct == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Physical product with id [" + id + "] not found.").build();
        }

        if (!physicalProduct.getSerialNumber().equals(serialNumber)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Physical product with id [" + id + "] not updated.").build();
        }
        return Response.status(Response.Status.OK).entity(toDTO(physicalProduct)).build();
    }

    // Delete
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) throws MyEntityNotFoundException {
        physicalProductBean.delete(id);

        if (physicalProductBean.exists(id)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Physical product with id [" + id + "] not deleted.").build();
        }
        return Response.status(Response.Status.OK).entity("Physical product with id [" + id + "] deleted.").build();
    }
}
