package com.example.backend.services;

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

    private ExternalContext securityContext;

    private LineOperatorDTO toDTO(LineOperator lineOperator) {
        LineOperatorDTO lineOperatorDTO = new LineOperatorDTO(
                lineOperator.getUsername(),
                lineOperator.getPassword(),
                lineOperator.getName(),
                lineOperator.getEmail()
        );
        return lineOperatorDTO;
    }

    // converts an entire list of entities into a list of DTOs
    private List<LineOperatorDTO> toDTOsLineOperator(List<LineOperator> lineOperators) {
        return lineOperators.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private OrderDTO toDTO(Order order) {
        return new OrderDTO(
//                order.getIdOrder(),
//                order.getOrderDate(),
//                order.getOrderType(),
//                order.getMaterialType()
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<OrderDTO> toDTOsOrder(List<Order> orders) {
        return orders.stream().map(this::toDTO).collect(Collectors.toList());
    }

//    private List<AlertDTO> toDTOsAlert(List<Alert> alerts) {
//        return alerts.stream().map(this::toDTO).collect(Collectors.toList());
//    }
//    private AlertDTO toDTO(Alert alert) {
//        return new AlertDTO(
//        );
//    }


    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/all") // means: the relative url path is “/api/lineOperator/”
    public List<LineOperatorDTO> getAllLineOperators() {
        return toDTOsLineOperator(lineOperatorBean.getAllLineOperators());
    }

    @POST
    @Path("/")
    public Response createNewLineOperator (LineOperatorDTO lineOperatorDTO)throws MyEntityExistsException, MyConstraintViolationException {
        lineOperatorBean.create(
                lineOperatorDTO.getUsername(),
                lineOperatorDTO.getPassword(),
                lineOperatorDTO.getName(),
                lineOperatorDTO.getEmail()
        );
        LineOperator lineOperator = lineOperatorBean.find(lineOperatorDTO.getUsername());
        return Response.status(Response.Status.CREATED).entity(toDTO(lineOperator)).build();
    }

    @GET
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response get(@PathParam("username") String username) {
//        var principal = securityContext.getUserPrincipal();
//        if(!principal.getName().equals(username)) {
//            return Response.status(Response.Status.FORBIDDEN).build();
//        }
        var entity = lineOperatorBean.find(username);
        if (entity == null) {
            var errorMsg = "Line Operator '%s' not found.".formatted(username);
            var status = Response.Status.NOT_FOUND;
            return Response.status(status).entity(errorMsg).build();
        }
        var dto = toDTO(entity);
        return Response.ok(dto).build();
    }

//    @GET
//    @Path("{username}/orders")
//    public Response getClientOrders(@PathParam("username") String username) {
//        var lineOperator = lineOperatorBean.getLineOperatorOrders(username);
//        var dtos = toDTOsOrder(lineOperator.getOrders());
//        return Response.ok(dtos).build();
//    }

//    @GET
//    @Path("{username}/alerts")
//    public Response getClientAlerts(@PathParam("username") String username) {
//        var client = lineOperatorBean.getLineOperatorAlerts(username);
//        var dtos = toDTOsAlert(client.getAlerts());
//        return Response.ok(dtos).build();
//    }

}
