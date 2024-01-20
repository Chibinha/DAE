package com.example.backend.services.unused;

import com.example.backend.dtos.DTOConverter;
import com.example.backend.entities.InventoryItem;
import com.example.backend.dtos.InventoryItemDTO;
import com.example.backend.ejbs.InventoryItemBean;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/InventoryItems")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class InventoryItemService {
    @EJB
    private InventoryItemBean inventoryItemBean;
    private final DTOConverter dtoConverter = new DTOConverter();

    // CRUD
    // Create
    @POST
    @Path("/")
    public Response create(InventoryItemDTO inventoryItemDTO, List<Long> productPackagesIds) throws MyEntityNotFoundException {
         long id = inventoryItemBean.create(inventoryItemDTO.getProductId(),productPackagesIds);
        if (id < 1) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Inventory Item with id [" + id + "] not created.").build();
        }
        return Response.status(Response.Status.CREATED).entity("Inventory Item with id [" + id + "] created.").build();
    }

    // Read
    @GET
    @Path("/")
    public List<InventoryItemDTO> getAll() {
        return dtoConverter.inventoryItemToDTOList(inventoryItemBean.getAll());
    }

    // Find
    @GET
    @Path("/{id}")
    public InventoryItemDTO find(@PathParam("id") long id) throws MyEntityNotFoundException {
        return dtoConverter.inventoryItemToDTO(inventoryItemBean.find(id));
    }

    // Update
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") long id, List<Long> productPackagesIds) throws MyEntityNotFoundException {
        inventoryItemBean.update(id, productPackagesIds);

        InventoryItem inventoryItem = inventoryItemBean.find(id);
        if (inventoryItem == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Inventory Item with id [" + id + "] not found.").build();
        }
        return Response.status(Response.Status.OK).entity(dtoConverter.inventoryItemToDTO(inventoryItem)).build();
    }

    // Delete
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) throws MyEntityNotFoundException {
        inventoryItemBean.delete(id);

        if (inventoryItemBean.exists(id)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Inventory Item with id [" + id + "] not deleted.").build();
        }
        return Response.status(Response.Status.OK).entity("Inventory Item with id [" + id + "] deleted.").build();
    }
}
