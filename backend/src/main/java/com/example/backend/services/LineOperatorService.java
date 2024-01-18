package com.example.backend.services;

import com.example.backend.dtos.DTOConverter;
import com.example.backend.dtos.LineOperatorDTO;
import com.example.backend.dtos.OrderDTO;
import com.example.backend.dtos.ProductDTO;
import com.example.backend.ejbs.LineOperatorBean;
import com.example.backend.ejbs.OrderBean;
import com.example.backend.entities.LineOperator;
import com.example.backend.entities.Order;
import com.example.backend.entities.Product;
import com.example.backend.exceptions.*;
import com.example.backend.exceptions.NotAuthorizedException;
import com.example.backend.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.transaction.Transactional;
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
    @EJB
    private OrderBean orderBean;
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
    public Response get(@PathParam("username") String username) throws MyEntityNotFoundException {
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
    @PUT
    @Path("{username}/")
    @Transactional
    public Response updateLineOperator(@PathParam("username") String username, LineOperatorDTO lineOperatorDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        lineOperatorBean.update(lineOperatorDTO.getUsername(),lineOperatorDTO.getPassword(), lineOperatorDTO.getName(), lineOperatorDTO.getEmail());
        boolean notUpdated = false;
        String notUpdatedFields = "";
        // check if fields changed with the update
        LineOperator lineOperator = lineOperatorBean.find(username);
        if(!lineOperatorDTO.getUsername().equals(lineOperator.getUsername()) && lineOperatorBean.exists(lineOperatorDTO.getUsername()))
        {
            throw new MyIllegalArgumentException("Nome de utilizador já está em uso. Nome de utilizador é unico");
        }

        if(lineOperatorDTO.getUsername() != null && !lineOperatorDTO.getUsername().equals(lineOperator.getUsername())) {
            notUpdated = true;
            notUpdatedFields += "username, ";
        }
        if(lineOperatorDTO.getPassword() != null && !lineOperatorDTO.getPassword().equals(lineOperator.getPassword())) {
            notUpdated = true;
            notUpdatedFields += "password, ";
        }
        if(lineOperatorDTO.getName() != null && !lineOperatorDTO.getName().equals(lineOperator.getName())) {
            notUpdated = true;
            notUpdatedFields += "name, ";
        }
        if(lineOperatorDTO.getEmail() != null && !lineOperatorDTO.getEmail().equals(lineOperator.getEmail())) {
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
            var lineOperator = lineOperatorBean.getLineOperatorOrders(username);
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
            return Response.ok(dtoConverter.orderToDTO(lineOperatorBean.getLineOperatorOrder(username,index))).build();
        }
    }

//    @PUT
//    @Path("{username}/orders/{index}")
//    @Transactional
//    public Response updateLineOperatorOrder(@PathParam("username") String username, @PathParam("index") long index, OrderDTO orderDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
//        orderBean.update(orderDTO.);
//        boolean notUpdated = false;
//        String notUpdatedFields = "";
//        // check if fields changed with the update
//        LineOperator lineOperator = lineOperatorBean.find(username);
//        if(!lineOperatorDTO.getUsername().equals(lineOperator.getUsername()) && lineOperatorBean.exists(lineOperatorDTO.getUsername()))
//        {
//            throw new MyIllegalArgumentException("Nome de utilizador já está em uso. Nome de utilizador é unico");
//        }
//
//        if(lineOperatorDTO.getUsername() != null && !lineOperatorDTO.getUsername().equals(lineOperator.getUsername())) {
//            notUpdated = true;
//            notUpdatedFields += "username, ";
//        }
//        if(lineOperatorDTO.getPassword() != null && !lineOperatorDTO.getPassword().equals(lineOperator.getPassword())) {
//            notUpdated = true;
//            notUpdatedFields += "password, ";
//        }
//        if(lineOperatorDTO.getName() != null && !lineOperatorDTO.getName().equals(lineOperator.getName())) {
//            notUpdated = true;
//            notUpdatedFields += "name, ";
//        }
//        if(lineOperatorDTO.getEmail() != null && !lineOperatorDTO.getEmail().equals(lineOperator.getEmail())) {
//            notUpdated = true;
//            notUpdatedFields += "email, ";
//        }
//
//        if (notUpdated) {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Line Operator  [" + username + "] did not update. Fields not updated: " + notUpdatedFields).build();
//        }
//        return Response.ok().build();
//    }

    @GET
    @Path("{username}/orders/{index}/products")
    @Transactional
    public Response getLineOperatorOrderProducts(@PathParam("username") String username, @PathParam("index") Long index) throws MyEntityNotFoundException, NotAuthorizedException {
        if(username.equals("anonymous"))
            return Response.status(Response.Status.NOT_FOUND).entity("You are not logged in. Please login.").build();
        else
        {
            return Response.ok(dtoConverter.productToDTOList(lineOperatorBean.getLineOperatorOrderProducts(username ,index))).build();
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
            return Response.ok(dtoConverter.observationToDTOList(lineOperatorBean.getLineOperatorOrderObservations(username ,index))).build();
        }
    }

//    @GET
//    @Path("{username}/alerts")
//    public Response getLineOperatorAlerts(@PathParam("username") String username) {
//        var lineOperator = lineOperatorBean.getLineOperatorAlerts(username);
//        var dtos = toDTOsAlert(lineOperator.getAlerts());
//        return Response.ok(dtos).build();
//    }

}
