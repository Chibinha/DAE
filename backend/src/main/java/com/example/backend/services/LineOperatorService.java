package com.example.backend.services;

import com.example.backend.dtos.DTOConverter;
import com.example.backend.dtos.LineOperatorDTO;
import com.example.backend.dtos.OrderDTO;
import com.example.backend.ejbs.LineOperatorBean;
import com.example.backend.entities.LineOperator;
import com.example.backend.entities.Order;
import com.example.backend.exceptions.MyConstraintViolationException;
import com.example.backend.exceptions.MyEntityExistsException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import com.example.backend.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("lineOperator") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
//@Authenticated
//@RolesAllowed({"LineOperator"})
public class LineOperatorService {

    @EJB
    private LineOperatorBean lineOperatorBean;
    private final DTOConverter dtoConverter = new DTOConverter();

    private ExternalContext securityContext;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/all") // means: the relative url path is “/api/lineOperator/”
    public List<LineOperatorDTO> getAll() {
        return dtoConverter.lineOperatorToDTOList(lineOperatorBean.getAll());
    }

    @POST
    @Path("/")
    public Response createNewLineOperator (LineOperatorDTO lineOperatorDTO) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        lineOperatorBean.create(
                lineOperatorDTO.getUsername(),
                lineOperatorDTO.getPassword(),
                lineOperatorDTO.getName(),
                lineOperatorDTO.getEmail()
        );
        LineOperator lineOperator = lineOperatorBean.find(lineOperatorDTO.getUsername());
        return Response.status(Response.Status.CREATED).entity(dtoConverter.lineOperatorToDTO(lineOperator)).build();
    }

    @GET
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response get(@PathParam("username") String username) {
//        var principal = securityContext.getUserPrincipal();
//        if(!principal.getName().equals(username)) {
//            return Response.status(Response.Status.FORBIDDEN).build();
//        }
        var lineOperator = lineOperatorBean.find(username);
        if (lineOperator == null) {
            var errorMsg = "Line Operator '%s' not found.".formatted(username);
            var status = Response.Status.NOT_FOUND;
            return Response.status(status).entity(errorMsg).build();
        }
        return Response.ok(dtoConverter.lineOperatorToDTO(lineOperator)).build();
    }

    @GET
    @Path("{username}/orders")
    public Response getClientOrders(@PathParam("username") String username) {
        var lineOperator = lineOperatorBean.getLineOperatorOrders(username);
        return Response.ok().build();
    }

//    @GET
//    @Path("{username}/alerts")
//    public Response getClientAlerts(@PathParam("username") String username) {
//        var client = lineOperatorBean.getLineOperatorAlerts(username);
//        var dtos = toDTOsAlert(client.getAlerts());
//        return Response.ok(dtos).build();
//    }

}
