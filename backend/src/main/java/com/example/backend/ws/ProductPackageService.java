package com.example.backend.ws;

import com.example.backend.dtos.ProductPackageDTO;
import com.example.backend.dtos.SensorDTO;
import com.example.backend.ejbs.ProductPackageBean;
import com.example.backend.entities.ProductPackage;
import com.example.backend.entities.Sensor;
import com.example.backend.entities.TransportationPackage;
import com.example.backend.exceptions.MyConstraintViolationException;
import com.example.backend.exceptions.MyEntityExistsException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Path("productpackages") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ProductPackageService {
    @EJB
    private ProductPackageBean productPackageBean;

    private ProductPackageDTO toDTONoSensorValues(ProductPackage productPackage) {
        return new ProductPackageDTO(
                productPackage.getId(),
                productPackage.getPackageType(),
                productPackage.getMaterial(),
                productPackage.getProduct().getId()
        );
    }

    private List<ProductPackageDTO> toDTOsNoSensorValues(List<ProductPackage> productPackages) {
        return productPackages.stream().map(this::toDTONoSensorValues).collect(Collectors.toList());
    }

    private ProductPackageDTO toDTO(ProductPackage productPackage) {
        return new ProductPackageDTO(
                productPackage.getId(),
                productPackage.getPackageType(),
                productPackage.getMaterial(),
                productPackage.getProduct().getId(),
                sensorToDTOs(productPackage.getValues())
        );
    }

    private List<ProductPackageDTO> toDTOs(List<ProductPackage> productPackages) {
        return productPackages.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private SensorDTO toDTO(Sensor sensor) {
        return new SensorDTO(
                sensor.getName(),
                sensor.getSensorType(),
                sensor.getUnit()
        );
    }

    private List<SensorDTO> sensorToDTOs(List<Sensor> sensors) {
        return sensors.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/")
    public List<ProductPackageDTO> getAllProductPackages() {
        return toDTOsNoSensorValues(productPackageBean.getAll());
    }

    @POST
    @Path("/")
    public Response createNewProductPackage(ProductPackageDTO productPackageDTO) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        productPackageBean.create(
                productPackageDTO.getId(), productPackageDTO.getPackageType(), productPackageDTO.getMaterial(), productPackageDTO.getProductId()
        );
        ProductPackage newProductPackage = productPackageBean.find(productPackageDTO.getId());
        if (newProductPackage == null)
            return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.status(Response.Status.CREATED).entity(toDTO(newProductPackage)).build();
    }

    @GET
    @Path("{id}")
    public Response getProductPackageDetails(@PathParam("id") long id) throws MyEntityNotFoundException {
        ProductPackage productPackage = productPackageBean.find(id);
        if (productPackage != null) {
            return Response.ok(toDTO(productPackage)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_PRODUCT_PACKAGE")
                .build();
    }

    @GET
    @Path("{id}/order")
    public Response getTransportationPackageSensorValues(@PathParam("id") long id) {
        ProductPackage productPackage = productPackageBean.find(id);
        if (productPackage != null) {
            List<SensorDTO> dtos = sensorToDTOs(productPackage.getValues());
            return Response.ok(dtos).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PRODUCT_PACKAGE").build();
    }
}
