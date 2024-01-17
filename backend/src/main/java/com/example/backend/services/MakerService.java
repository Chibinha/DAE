package com.example.backend.services;

import com.example.backend.dtos.DTOConverter;
import com.example.backend.dtos.PhysicalProductDTO;
import com.example.backend.dtos.ProductDTO;
import com.example.backend.ejbs.MakerBean;
import com.example.backend.ejbs.PhysicalProductBean;
import com.example.backend.ejbs.ProductBean;
import com.example.backend.entities.Alert;
import com.example.backend.entities.PhysicalProduct;
import com.example.backend.entities.Product;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("maker/{username}")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class MakerService {
    @EJB
    private MakerBean makerBean;
    private final DTOConverter dtoConverter = new DTOConverter();

    //Get Own products
    @GET
    @Path("/products")
    public List<ProductDTO> getProducts(@PathParam("username") String username) throws MyEntityNotFoundException {
        List<Product> products = makerBean.getProducts(username);
        return dtoConverter.productToDTOList(products);
    }

    //Get Own pshysical products
    @GET
    @Path("/physicalproducts")
    public List<PhysicalProductDTO> getPhysicalProducts(@PathParam("username") String username) throws MyEntityNotFoundException {
        List<PhysicalProduct> physicalProducts = makerBean.getPhysicalProducts(username);
        return dtoConverter.physicalProductToDTOList(physicalProducts);
    }

    @GET
    @Path("/physicalproducts/{productId}")
    public List<PhysicalProductDTO> getPhysicalProductsForProduct(@PathParam("username") String username, @PathParam("productId") long productId) throws MyEntityNotFoundException {
        List<PhysicalProduct> physicalProducts = makerBean.getPhysicalProductsForProduct(username, productId);
        return dtoConverter.physicalProductToDTOList(physicalProducts);
    }

    //Get Alerts
    @GET
    @Path("/alerts")
    public Response getAlerts(@PathParam("username") String username) throws MyEntityNotFoundException {
        List<Alert> alerts = makerBean.getAlerts(username);
        return Response.ok(dtoConverter.alertToDTOList(alerts)).build();
    }

    //Create new product
    @POST
    @Path("/products")
    public Response createProduct(ProductDTO productDTO, @PathParam("username") String username) throws MyEntityNotFoundException {
        long id = makerBean.createProduct(
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

    //Create new physical product
    @POST
    @Path("/physicalproducts")
    public Response createPhysicalProduct(PhysicalProductDTO physicalProductDTO, @PathParam("username") String username) throws MyEntityNotFoundException {
        long id = makerBean.createPhysicalProduct(
                physicalProductDTO.getSerialNumber(),
                physicalProductDTO.getProductId()
        );

        if (id < 1) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Physical Product with id [" + id + "] not created.").build();
        }
        return Response.status(Response.Status.CREATED).entity("Physical Product with id [" + id + "] created.").build();
    }

    // Create new List of physical product
    @POST
    @Path("/physicalproducts/list")
    public Response createPhysicalProductList(List<PhysicalProductDTO> physicalProductDTOList, @PathParam("username") String username) throws MyEntityNotFoundException {
        makerBean.createPhysicalProductList(physicalProductDTOList);
        return Response.status(Response.Status.CREATED).entity("Physical Products created.").build();
    }
}
