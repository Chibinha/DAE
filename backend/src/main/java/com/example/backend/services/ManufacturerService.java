package com.example.backend.services;

import com.example.backend.dtos.DTOConverter;
import com.example.backend.dtos.InventoryItemDTO;
import com.example.backend.dtos.ProductDTO;
import com.example.backend.ejbs.ManufacturerBean;
import com.example.backend.ejbs.UserBean;
import com.example.backend.entities.Alert;
import com.example.backend.entities.InventoryItem;
import com.example.backend.entities.Product;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@Path("manufacturer/{username}")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ManufacturerService {
    @EJB
    private ManufacturerBean manufacturerBean;
    @EJB
    private UserBean userBean;
    private final DTOConverter dtoConverter = new DTOConverter();

    //#region Products
    //Create new product
    @POST
    @Path("/products")
    public Response createProduct(ProductDTO productDTO, @PathParam("username") String username) throws MyEntityNotFoundException {
        long id = manufacturerBean.createProduct(
                productDTO.getName(),
                productDTO.getPrice(),
                productDTO.getDescription(),
                productDTO.getWeight(),
                productDTO.getIngredients(),
                username
        );

        if (id < 1) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Product with id [" + id + "] not created.").build();
        }
        return Response.status(Response.Status.CREATED).entity("Product with id [" + id + "] created.").build();
    }

    @PUT
    @Path("/products/{productId}")
    public Response updateProduct(@PathParam("productId") long productId, ProductDTO productDTO) throws MyEntityNotFoundException {
        long id = manufacturerBean.updateProduct(productId, productDTO);
        if (id < 1) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Product with id [" + id + "] not updated.").build();
        }
        return Response.status(Response.Status.CREATED).entity("Product with id [" + id + "] updated.").build();
    }

    //Get Own products
    @GET
    @Path("/products")
    public List<ProductDTO> getProducts(@PathParam("username") String username) throws MyEntityNotFoundException {
        List<Product> products = manufacturerBean.getProducts(username);
        return dtoConverter.productToDTOList(products);
    }
    //#endregion


    //#region PhysicalProducts
    //Create new physical product
    @POST
    @Path("/physicalproducts")
    public Response createPhysicalProduct(long productId) throws MyEntityNotFoundException {
        long id = manufacturerBean.createPhysicalProduct(productId);

        if (id < 1) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Physical Product with id [" + id + "] not created.").build();
        }
        return Response.status(Response.Status.CREATED).entity("Physical Product with id [" + id + "] created.").build();
    }

    // Create new List of physical product
    @POST
    @Path("/physicalproducts/list")
    public Response createPhysicalProductList(Map<String, Integer> requestData) throws MyEntityNotFoundException {
        manufacturerBean.createPhysicalProductList(requestData.get("productId"), requestData.get("amount"));
        return Response.status(Response.Status.CREATED).entity("Physical Products created.").build();
    }

    //update PhyscialProduct
    @PUT
    @Path("/physicalproducts/{physicalProductId}")
    public Response updatePhysicalProduct(@PathParam("physicalProductId") long physicalProductId, InventoryItemDTO inventoryItemDTO) throws MyEntityNotFoundException {
        long id = manufacturerBean.updatePhysicalProduct(physicalProductId);
        if (id < 1) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Physical Product with id [" + id + "] not updated.").build();
        }
        return Response.status(Response.Status.CREATED).entity("Physical Product with id [" + id + "] updated.").build();
    }

    //Delete InventoryItem
    @DELETE
    @Path("/physicalproducts/{physicalProductId}")
    public Response deletePhysicalProduct(@PathParam("physicalProductId") long physicalProductId) throws MyEntityNotFoundException {
        manufacturerBean.deletePhysicalProduct(physicalProductId);
        return Response.status(Response.Status.OK).entity("Physical Product with id [" + physicalProductId + "] deleted.").build();
    }

    //Get Own physical products
    @GET
    @Path("/physicalproducts")
    public List<InventoryItemDTO> getPhysicalProducts(@PathParam("username") String username) throws MyEntityNotFoundException {
        List<InventoryItem> inventoryItems = manufacturerBean.getAllPhysicalProducts(username);
        return dtoConverter.physicalProductToDTOList(inventoryItems);
    }

    @GET
    @Path("/physicalproducts/{productId}")
    public List<InventoryItemDTO> getPhysicalProductsForProduct(@PathParam("username") String username, @PathParam("productId") long productId) throws MyEntityNotFoundException {
        List<InventoryItem> inventoryItems = manufacturerBean.getPhysicalProductsForProduct(username, productId);
        return dtoConverter.physicalProductToDTOList(inventoryItems);
    }
    //#endregion


    //#region Alerts
    //Get Alerts
    @GET
    @Path("{username}/alerts")
    public Response getAlerts(@PathParam("username") String username) throws MyEntityNotFoundException {
        List<Alert> alerts = userBean.getAlerts(username);
        return Response.ok(dtoConverter.alertToDTOList(alerts)).build();
    }
    //#endregion

}
