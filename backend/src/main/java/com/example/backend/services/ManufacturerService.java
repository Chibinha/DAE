package com.example.backend.services;

import com.example.backend.dtos.DTOConverter;
import com.example.backend.dtos.InventoryItemDTO;
import com.example.backend.dtos.ProductDTO;
import com.example.backend.ejbs.ManufacturerBean;
import com.example.backend.ejbs.UserBean;
import com.example.backend.entities.Alert;
import com.example.backend.entities.InventoryItem;
import com.example.backend.entities.Product;
import com.example.backend.entities.ProductPackage;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

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
    @Consumes(MediaType.APPLICATION_JSON)
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


    //#region InventoryItem
    // Create new List of InventoryItems
    @POST
    @Path("/items/{productId}/{amount}")
    public Response createInventoryItemList(@PathParam("productId") long productId, @PathParam("amount") long amount, List<Long> productPackageIds) throws MyEntityNotFoundException {
        manufacturerBean.createInventoryItemList(productId, (int)amount, productPackageIds);

        return Response.status(Response.Status.CREATED).entity("Inventory Items created.").build();
    }

    //update InventoryItem
    @PUT
    @Path("/items/{itemId}")
    public Response updateInventoryItem(@PathParam("itemId") long itemId, List<Long> packageIds ) throws MyEntityNotFoundException {
        long id = manufacturerBean.updateInventoryItem(itemId, packageIds);
        if (id < 1) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Inventory Item with id [" + id + "] not updated.").build();
        }
        return Response.status(Response.Status.CREATED).entity("Inventory Item with id [" + id + "] updated.").build();
    }

    //Delete InventoryItem
    @DELETE
    @Path("/items/{itemId}")
    public Response deleteInventoryItem(@PathParam("itemId") long itemId) throws MyEntityNotFoundException {
        manufacturerBean.deleteInventoryItem(itemId);
        return Response.status(Response.Status.OK).entity("Inventory Item with id [" + itemId + "] deleted.").build();
    }

    //Get Own InventoryItems
    @GET
    @Path("/items")
    public List<InventoryItemDTO> getInventoryItems(@PathParam("username") String username) throws MyEntityNotFoundException {
        List<InventoryItem> inventoryItems = manufacturerBean.getAllInventoryItems(username);
        return dtoConverter.inventoryItemToDTOList(inventoryItems);
    }

    @GET
    @Path("/items/{productId}")
    public List<InventoryItemDTO> getInventoryItemsProduct(@PathParam("username") String username, @PathParam("productId") long productId) throws MyEntityNotFoundException {
        List<InventoryItem> inventoryItems = manufacturerBean.getInventoryItemsForProduct(username, productId);
        return dtoConverter.inventoryItemToDTOList(inventoryItems);
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


    //#region Packages
    //Get packages
    @GET
    @Path("/packages")
    public Response getPackages(@PathParam("username") String username) throws MyEntityNotFoundException {
        List<ProductPackage> productPackages = manufacturerBean.getPackages(username);
        return Response.ok(dtoConverter.productPackageToDTOList(productPackages)).build();
    }
    //#endregion
}
