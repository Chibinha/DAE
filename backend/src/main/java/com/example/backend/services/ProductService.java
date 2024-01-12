package com.example.backend.services;

import com.example.backend.dtos.ProductDTO;
import com.example.backend.entities.Product;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import com.example.backend.ejbs.ProductBean;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/products")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProductService {
    @EJB
    private ProductBean productBean;

    private ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getWeight(),
                product.getIngredients()
        );
    }

    private List<ProductDTO> toDTOs(List<Product> products) {
        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Create
    @POST
    @Path("/")
    public Response create(ProductDTO productDTO) throws MyEntityNotFoundException {
        long id = productBean.create(
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getWeight(),
                productDTO.getIngredients()
        );
        Product newProduct = productBean.find(id);
        if (newProduct == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Product with id" + id + "not found.").build();
        }
        return Response.status(Response.Status.CREATED).entity(toDTO(newProduct)).build();
    }

    // Read
    @GET
    @Path("/")
    public Response getAll() {
        if (productBean.getAll().isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No products found.").build();
        }

        return Response.ok(toDTOs(productBean.getAll())).build();
    }

    // Find
    @GET
    @Path("/{id}")
    public Response find(@PathParam("id") long id) throws MyEntityNotFoundException {
        Product product = productBean.find(id);

        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Product with id [" + id + "] not found.").build();
        }

        return Response.ok(toDTO(product)).build();
    }

    // Update
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") long id, ProductDTO productDTO) throws MyEntityNotFoundException {
        productBean.update(id, productDTO);
        boolean notUpdated = false;
        String notUpdatedFields = "";
        // check if fields changed with the update
        Product product = productBean.find(id);
        if(productDTO.getName() != null && !productDTO.getName().equals(product.getName())) {
            notUpdated = true;
            notUpdatedFields += "name, ";
        }
        if(productDTO.getDescription() != null && !productDTO.getDescription().equals(product.getDescription())){
            notUpdated = true;
            notUpdatedFields += "description, ";
        }
        if(productDTO.getWeight() != 0 && productDTO.getWeight() != product.getWeight()){
            notUpdated = true;
            notUpdatedFields += "weight, ";
        }
        if(productDTO.getIngredients() != null && !productDTO.getIngredients().equals(product.getIngredients())){
            notUpdated = true;
            notUpdatedFields += "ingredients, ";
        }

        if (notUpdated) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Product with id [" + id + "] not updated. Fields not updated: " + notUpdatedFields).build();
        }
        return Response.ok().build();
    }

    // Delete
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) throws MyEntityNotFoundException {
        productBean.delete(id);

        if (productBean.exists(id)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Product with id [" + id + "] not deleted.").build();
        }
        return Response.status(Response.Status.OK).entity("Product with id [" + id + "] deleted.").build();
    }
}
