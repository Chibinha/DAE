package com.example.backend.services;

import com.example.backend.dtos.DTOConverter;
import com.example.backend.dtos.SensorDTO;
import com.example.backend.dtos.TransportationPackageDTO;
import com.example.backend.ejbs.TransportationPackageBean;
import com.example.backend.entities.Sensor;
import com.example.backend.entities.TransportationPackage;
import com.example.backend.exceptions.MyConstraintViolationException;
import com.example.backend.exceptions.MyEntityExistsException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("transpotationpackage") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class TransportationPackageService {
    @EJB
    private TransportationPackageBean transportationPackageBean;
    private final DTOConverter dtoConverter = new DTOConverter();
    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/")
    public List<TransportationPackageDTO> getAllTransportationPackages() {
        return dtoConverter.transportationPackageToDTOList(transportationPackageBean.getAll());
    }

    @POST
    @Path("/")
    public Response createNewTransportationPackage(TransportationPackageDTO transportationPackageDTO) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        transportationPackageBean.create(
                transportationPackageDTO.getId(), transportationPackageDTO.getPackageType(), transportationPackageDTO.getMaterial(), transportationPackageDTO.getOrderId()
        );
        TransportationPackage newTransportationPackage = transportationPackageBean.find(transportationPackageDTO.getId());
        if (newTransportationPackage == null)
            return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.status(Response.Status.CREATED).entity(dtoConverter.transportationPackageToDTO(newTransportationPackage)).build();
    }

    @GET
    @Path("{id}")
    public Response getTransportationPackageDetails(@PathParam("id") long id) throws MyEntityNotFoundException {
        TransportationPackage transportationPackage = transportationPackageBean.find(id);
        if (transportationPackage != null) {
            return Response.ok(dtoConverter.transportationPackageToDTO(transportationPackage)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_TRANSPORTATION_PACKAGE")
                .build();
    }

//    @GET
//    @Path("{id}/order")
//    public Response getTransportationPackageSensorValues(@PathParam("id") long id) {
//        TransportationPackage transportationPackage = transportationPackageBean.find(id);
//        if (transportationPackage != null) {
//            List<SensorDTO> dtos = sensorToDTOs(transportationPackage.getSensor());
//            return Response.ok(dtos).build();
//        }
//        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_TRANSPORTATION_PACKAGE").build();
//    }
}
