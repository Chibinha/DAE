package com.example.backend.services.unused;

import com.example.backend.dtos.DTOConverter;
import com.example.backend.dtos.SensorDTO;
import com.example.backend.dtos.TransportPackageDTO;
import com.example.backend.ejbs.TransportPackageBean;
import com.example.backend.entities.TransportPackage;
import com.example.backend.exceptions.MyEntityExistsException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("transportationpackage") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class TransportPackageService {
    @EJB
    private TransportPackageBean transportPackageBean;
    private final DTOConverter dtoConverter = new DTOConverter();
    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/all")
    @Transactional
    public List<TransportPackageDTO> getAllTransportationPackages() {
        return dtoConverter.transportationPackageToDTOList(transportPackageBean.getAll());
    }

    @POST
    @Path("/")
    public Response createNewTransportationPackage(TransportPackageDTO transportationPackageDTO) throws  MyEntityNotFoundException {
        transportPackageBean.create(transportationPackageDTO.getPackageType(), transportationPackageDTO.getMaterial());
        TransportPackage newTransportationPackage = transportPackageBean.find(transportationPackageDTO.getId());
        if (newTransportationPackage == null)
            return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.status(Response.Status.CREATED).entity(dtoConverter.transportationPackageToDTO(newTransportationPackage)).build();
    }

    @GET
    @Path("{id}")
    public Response getTransportationPackageDetails(@PathParam("id") long id) throws MyEntityNotFoundException {
        TransportPackage transportationPackage = transportPackageBean.find(id);
        if (transportationPackage != null) {
            return Response.ok(dtoConverter.transportationPackageToDTO(transportationPackage)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_TRANSPORTATION_PACKAGE")
                .build();
    }
}
