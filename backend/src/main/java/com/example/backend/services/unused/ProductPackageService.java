package com.example.backend.services.unused;

import com.example.backend.dtos.DTOConverter;
import com.example.backend.dtos.ProductPackageDTO;
import com.example.backend.ejbs.ProductPackageBean;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("productpackages") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ProductPackageService {
    @EJB
    private ProductPackageBean productPackageBean;
    private final DTOConverter dtoConverter = new DTOConverter();
    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/")
    public List<ProductPackageDTO> getAllProductPackages() {
        return dtoConverter.productPackageToDTOList(productPackageBean.getAll());
    }
//
//    @POST
//    @Path("/")
//    public Response createNewProductPackage(ProductPackageDTO productPackageDTO) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
//        productPackageBean.create(
//                productPackageDTO.getId(), productPackageDTO.getPackageType(), productPackageDTO.getMaterial(), productPackageDTO.getProductId()
//        );
//        ProductPackage newProductPackage = productPackageBean.find(productPackageDTO.getId());
//        if (newProductPackage == null)
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        return Response.status(Response.Status.CREATED).entity(dtoConverter.productPackageToDTO(newProductPackage)).build();
//    }
//
//    @GET
//    @Path("{id}")
//    public Response getProductPackageDetails(@PathParam("id") long id) throws MyEntityNotFoundException {
//        ProductPackage productPackage = productPackageBean.find(id);
//        if (productPackage != null) {
//            return Response.ok(dtoConverter.productPackageToDTO(productPackage)).build();
//        }
//        return Response.status(Response.Status.NOT_FOUND)
//                .entity("ERROR_FINDING_PRODUCT_PACKAGE")
//                .build();
//    }
//
//    @GET
//    @Path("{id}/order")
//    public Response getTransportationPackageSensorValues(@PathParam("id") long id) {
//        ProductPackage productPackage = productPackageBean.find(id);
//        if (productPackage != null) {
//            List<SensorDTO> dtos = sensorToDTOs(productPackage.getSensor());
//            return Response.ok(dtos).build();
//        }
//        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PRODUCT_PACKAGE").build();
//    }
}
