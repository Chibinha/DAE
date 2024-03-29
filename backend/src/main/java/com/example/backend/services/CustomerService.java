package com.example.backend.services;

import com.example.backend.dtos.CustomerDTO;
import com.example.backend.dtos.DTOConverter;
import com.example.backend.dtos.OrderDTO;
import com.example.backend.dtos.ProductDTO;
import com.example.backend.ejbs.AlertBean;
import com.example.backend.ejbs.CustomerBean;
import com.example.backend.ejbs.UserBean;
import com.example.backend.entities.Alert;
import com.example.backend.entities.Customer;
import com.example.backend.exceptions.MyConstraintViolationException;
import com.example.backend.exceptions.MyEntityExistsException;
import com.example.backend.exceptions.MyEntityNotFoundException;
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
import java.util.Map;

@Path("customer") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
@RolesAllowed({"Customer"})
@Transactional
public class CustomerService {
    @EJB
    private AlertBean alertBean;
    @EJB
    private UserBean userBean;
    @EJB
    private CustomerBean customerBean;
    private final DTOConverter dtoConverter = new DTOConverter();
    private ExternalContext securityContext;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/all") // means: the relative url path is “/api/clients/”
    public List<CustomerDTO> getAllClients() {
        return dtoConverter.clientToDTOList(customerBean.getAllClients());
    }

    @POST
    @Path("/")
    public Response createNewClient (CustomerDTO customerDTO) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        customerBean.create(
                customerDTO.getUsername(),
                customerDTO.getPassword(),
                customerDTO.getName(),
                customerDTO.getEmail()
        );
        Customer customer = customerBean.find(customerDTO.getUsername());
        return Response.status(Response.Status.CREATED).entity(dtoConverter.clientToDTO(customer)).build();
    }

    @GET
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response get(@PathParam("username") String username) throws MyEntityNotFoundException {
//        var principal = securityContext.getUserPrincipal();
//        if(!principal.getName().equals(username)) {
//            return Response.status(Response.Status.FORBIDDEN).build();
//        }
        var client = customerBean.find(username);
        if (client == null) {
            var errorMsg = "Customer '%s' not found.".formatted(username);
            var status = Response.Status.NOT_FOUND;
            return Response.status(status).entity(errorMsg).build();
        }
        return Response.ok(dtoConverter.clientToDTO(client)).build();
    }

    @GET
    @Path("{username}/orders")
    public Response getClientOrders(@PathParam("username") String username) throws MyEntityNotFoundException {
        if(username.equals("anonymous"))
            return Response.status(Response.Status.NOT_FOUND).entity("You are not logged in. Please login.").build();
        else
        {
            var client = customerBean.getClientOrders(username);
            return Response.ok(dtoConverter.orderToDTOList(client.getOrders())).build();
        }
    }

    @GET
    @Path("{username}/alerts")
    public Response getClientAlerts(@PathParam("username") String username) throws MyEntityNotFoundException {
        List<Alert> alerts = alertBean.getUserAlerts(username);
        return Response.ok(dtoConverter.alertToDTOList(alerts)).build();
    }


    @GET
    @Path("{username}/orders/{index}")
    public Response getClientOrder(@PathParam("username") String username, @PathParam("index") Long index) throws MyEntityNotFoundException, NotAuthorizedException {
        if(username.equals("anonymous"))
            return Response.status(Response.Status.NOT_FOUND).entity("You are not logged in. Please login.").build();
        else
        {
            return Response.ok(dtoConverter.orderToDTO(customerBean.getClientOrder(username ,index))).build();
        }
    }

    @GET
    @Path("{username}/orders/{index}/products")
    public Response getClientOrderProducts(@PathParam("username") String username, @PathParam("index") Long index) throws MyEntityNotFoundException, NotAuthorizedException {
        if(username.equals("anonymous"))
            return Response.status(Response.Status.NOT_FOUND).entity("You are not logged in. Please login.").build();
        else
        {
            return Response.ok(dtoConverter.productToDTOList(customerBean.getClientOrderProducts(username ,index))).build();
        }
    }

    @GET
    @Path("{username}/orders/{index}/observations")
    public Response getClientOrderObservations(@PathParam("username") String username, @PathParam("index") Long index) throws MyEntityNotFoundException {
        if(username.equals("anonymous"))
            return Response.status(Response.Status.NOT_FOUND).entity("You are not logged in. Please login.").build();
        else
        {
            return Response.ok(dtoConverter.observationToDTOList(customerBean.getClientOrderObservations(username ,index))).build();
        }
    }

    @GET
    @Path("{username}/alerts")
    public Response getAlerts(@PathParam("username") String username) throws MyEntityNotFoundException {
        List<Alert> alerts = userBean.getAlerts(username);
        return Response.ok(dtoConverter.alertToDTOList(alerts)).build();
    }

    @POST
    @Path("{username}/orders")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(Map<Long, Integer> products, @PathParam("username") String username) throws MyEntityNotFoundException, MyConstraintViolationException, MyEntityExistsException {
        long id = customerBean.createOrder(
            username,
            products
        );
        if (id < 1) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Order with id [" + id + "] not created.").build();
        }
        return Response.status(Response.Status.CREATED).entity("Order with id [" + id + "] created.").build();
    }
}
