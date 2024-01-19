package com.example.backend.services.unused;

import com.example.backend.dtos.DTOConverter;
import com.example.backend.dtos.TransportPackageDTO;
import com.example.backend.ejbs.TransportPackageBean;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("transpotationpackage") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class TransportPackageService {
    @EJB
    private TransportPackageBean transportPackageBean;
    private final DTOConverter dtoConverter = new DTOConverter();
    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/")
    public List<TransportPackageDTO> getAllTransportationPackages() {
        return dtoConverter.transportationPackageToDTOList(transportPackageBean.getAll());
    }
//
//    @POST
//    @Path("/")
//    public Response createNewTransportationPackage(TransportPackageDTO transportationPackageDTO) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
//        transportPackageBean.create(
//                transportationPackageDTO.getId(), transportationPackageDTO.getPackageType(), transportationPackageDTO.getMaterial(), transportationPackageDTO.getOrderId()
//        );
//        TransportPackage newTransportationPackage = transportPackageBean.find(transportationPackageDTO.getId());
//        if (newTransportationPackage == null)
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        return Response.status(Response.Status.CREATED).entity(dtoConverter.transportationPackageToDTO(newTransportationPackage)).build();
//    }
//
//    @GET
//    @Path("{id}")
//    public Response getTransportationPackageDetails(@PathParam("id") long id) throws MyEntityNotFoundException {
//        TransportPackage transportationPackage = transportPackageBean.find(id);
//        if (transportationPackage != null) {
//            return Response.ok(dtoConverter.transportationPackageToDTO(transportationPackage)).build();
//        }
//        return Response.status(Response.Status.NOT_FOUND)
//                .entity("ERROR_FINDING_TRANSPORTATION_PACKAGE")
//                .build();
//    }

//    @GET
//    @Path("{id}/order")
//    public Response getTransportationPackageSensorValues(@PathParam("id") long id) {
//        TransportPackage transportationPackage = transportPackageBean.find(id);
//        if (transportationPackage != null) {
//            List<SensorDTO> dtos = sensorToDTOs(transportationPackage.getSensor());
//            return Response.ok(dtos).build();
//        }
//        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_TRANSPORTATION_PACKAGE").build();
//    }
}
