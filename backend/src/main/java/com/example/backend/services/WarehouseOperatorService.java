package com.example.backend.services;

import com.example.backend.dtos.*;
import com.example.backend.ejbs.WarehouseOperatorBean;
import com.example.backend.entities.WarehouseOperator;
import com.example.backend.exceptions.*;
import com.example.backend.exceptions.NotAuthorizedException;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("warehouseoperator") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
//@Authenticated
//@RolesAllowed({"WarehouseOperator"})
public class WarehouseOperatorService {

    @EJB
    private WarehouseOperatorBean warehouseOperatorBean;
    private final DTOConverter dtoConverter = new DTOConverter();

    private ExternalContext securityContext;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/all") // means: the relative url path is “/api/warehouseOperator/”
    public List<WarehouseOperatorDTO> getAll() {
        return dtoConverter.lineOperatorToDTOList(warehouseOperatorBean.getAll());
    }

    @POST
    @Path("/")
    public Response createNewLineOperator (WarehouseOperatorDTO warehouseOperatorDTO) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        warehouseOperatorBean.create(
                warehouseOperatorDTO.getUsername(),
                warehouseOperatorDTO.getPassword(),
                warehouseOperatorDTO.getName(),
                warehouseOperatorDTO.getEmail()
        );
        WarehouseOperator warehouseOperator = warehouseOperatorBean.find(warehouseOperatorDTO.getUsername());
        return Response.status(Response.Status.CREATED).entity(dtoConverter.lineOperatorToDTO(warehouseOperator)).build();
    }

    @GET
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response get(@PathParam("username") String username) throws MyEntityNotFoundException {
//        var principal = securityContext.getUserPrincipal();
//        if(!principal.getName().equals(username)) {
//            return Response.status(Response.Status.FORBIDDEN).build();
//        }
        var lineOperator = warehouseOperatorBean.find(username);
        if (lineOperator == null) {
            var errorMsg = "Line Operator '%s' not found.".formatted(username);
            var status = Response.Status.NOT_FOUND;
            return Response.status(status).entity(errorMsg).build();
        }
        return Response.ok(dtoConverter.lineOperatorToDTO(lineOperator)).build();
    }
    @PUT
    @Path("{username}/")
    @Transactional
    public Response updateLineOperator(@PathParam("username") String username, WarehouseOperatorDTO warehouseOperatorDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        warehouseOperatorBean.update(warehouseOperatorDTO.getUsername(), warehouseOperatorDTO.getPassword(), warehouseOperatorDTO.getName(), warehouseOperatorDTO.getEmail());
        boolean notUpdated = false;
        String notUpdatedFields = "";
        // check if fields changed with the update
        WarehouseOperator warehouseOperator = warehouseOperatorBean.find(username);
        if(!warehouseOperatorDTO.getUsername().equals(warehouseOperator.getUsername()) && warehouseOperatorBean.exists(warehouseOperatorDTO.getUsername()))
        {
            throw new MyIllegalArgumentException("Nome de utilizador já está em uso. Nome de utilizador é unico");
        }

        if(warehouseOperatorDTO.getUsername() != null && !warehouseOperatorDTO.getUsername().equals(warehouseOperator.getUsername())) {
            notUpdated = true;
            notUpdatedFields += "username, ";
        }
        if(warehouseOperatorDTO.getPassword() != null && !warehouseOperatorDTO.getPassword().equals(warehouseOperator.getPassword())) {
            notUpdated = true;
            notUpdatedFields += "password, ";
        }
        if(warehouseOperatorDTO.getName() != null && !warehouseOperatorDTO.getName().equals(warehouseOperator.getName())) {
            notUpdated = true;
            notUpdatedFields += "name, ";
        }
        if(warehouseOperatorDTO.getEmail() != null && !warehouseOperatorDTO.getEmail().equals(warehouseOperator.getEmail())) {
            notUpdated = true;
            notUpdatedFields += "email, ";
        }

        if (notUpdated) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Line Operator  [" + username + "] did not update. Fields not updated: " + notUpdatedFields).build();
        }
        return Response.ok().build();
    }

    @GET
    @Path("{username}/orders")
    @Transactional
    public Response getLineOperatorOrders(@PathParam("username") String username) throws MyEntityNotFoundException {
        if(username.equals("anonymous"))
            return Response.status(Response.Status.NOT_FOUND).entity("You are not logged in. Please login.").build();
        else
        {
            var lineOperator = warehouseOperatorBean.getLineOperatorOrders(username);
            return Response.ok(dtoConverter.orderToDTOList(lineOperator.getOrders())).build();
        }
    }

    @GET
    @Path("{username}/orders/{index}")
    @Transactional
    public Response getLineOperatorOrder(@PathParam("username") String username, @PathParam("index") long index) throws MyEntityNotFoundException, NotAuthorizedException {
        if(username.equals("anonymous"))
            return Response.status(Response.Status.NOT_FOUND).entity("You are not logged in. Please login.").build();
        else
        {
            return Response.ok(dtoConverter.orderToDTO(warehouseOperatorBean.getLineOperatorOrder(username,index))).build();
        }
    }

    @PATCH
    @Path("{username}/orders/{index}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateLineOperatorOrder(@PathParam("username") String username, @PathParam("index") int index, OrderDTO orderDTO) throws MyEntityNotFoundException, MyConstraintViolationException, MyEntityExistsException {
        warehouseOperatorBean.updateOrder(index, orderDTO.getPackageId(), orderDTO.getSensorId(), orderDTO.getStatus());
        if (index < 1) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Order with id [" + index + "] not updated.").build();
        }
        return Response.status(Response.Status.OK).entity("Order with id [" + index + "] updated.").build();
    }

    @PUT
    @Path("{username}/orders/{index}")
    @Transactional
    public Response updateLineOperatorOrderSensor(@PathParam("username") String username, @PathParam("index") long index, SensorDTO sensorDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        return Response.ok().build();
    }

    @PUT
    @Path("{username}/orders/{index}")
    @Transactional
    public Response updateLineOperatorOrderPackage(@PathParam("username") String username, @PathParam("index") long index, TransportPackageDTO packageDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        return Response.ok().build();
    }

    @GET
    @Path("{username}/orders/{index}/products")
    @Transactional
    public Response getLineOperatorOrderProducts(@PathParam("username") String username, @PathParam("index") Long index) throws MyEntityNotFoundException, NotAuthorizedException {
        if(username.equals("anonymous"))
            return Response.status(Response.Status.NOT_FOUND).entity("You are not logged in. Please login.").build();
        else
        {
            return Response.ok(dtoConverter.productToDTOList(warehouseOperatorBean.getLineOperatorOrderProducts(username ,index))).build();
        }
    }

    @GET
    @Path("{username}/orders/{index}/observations")
    @Transactional
    public Response getLineOperatorOrderObservations(@PathParam("username") String username, @PathParam("index") Long index) throws MyEntityNotFoundException {
        if(username.equals("anonymous"))
            return Response.status(Response.Status.NOT_FOUND).entity("You are not logged in. Please login.").build();
        else
        {
            return Response.ok(dtoConverter.observationToDTOList(warehouseOperatorBean.getLineOperatorOrderObservations(username ,index))).build();
        }
    }

    @GET
    @Path("{username}/alerts")
    public Response getAlerts(@PathParam("username") String username) throws MyEntityNotFoundException {
//        List<Alert> alerts = userBean.getAlerts(username);
//        return Response.ok(dtoConverter.alertToDTOList(alerts)).build();
        return Response.ok().build();
    }

//    @GET
//    @Path("{username}/alerts")
//    public Response getLineOperatorAlerts(@PathParam("username") String username) {
//        var warehouseOperator = warehouseOperatorBean.getLineOperatorAlerts(username);
//        var dtos = toDTOsAlert(warehouseOperator.getAlerts());
//        return Response.ok(dtos).build();
//    }

}
