package com.example.backend.services;

import com.example.backend.dtos.AlertDTO;
import com.example.backend.dtos.ClientDTO;
import com.example.backend.dtos.DTOConverter;
import com.example.backend.dtos.OrderDTO;
import com.example.backend.ejbs.AlertBean;
import com.example.backend.ejbs.ClientBean;
import com.example.backend.entities.Alert;
import com.example.backend.entities.Client;
import com.example.backend.entities.Order;
import com.example.backend.entities.Product;
import com.example.backend.exceptions.MyConstraintViolationException;
import com.example.backend.exceptions.MyEntityExistsException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import com.example.backend.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.mail.MessagingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("client") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
//@Authenticated
//@RolesAllowed({"Client"})
public class ClientService {
    @EJB
    private ClientBean clientBean;
    @EJB
    private AlertBean alertBean;
    private final DTOConverter dtoConverter = new DTOConverter();
    private ExternalContext securityContext;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/all") // means: the relative url path is “/api/clients/”
    public List<ClientDTO> getAllClients() {
        return dtoConverter.clientToDTOList(clientBean.getAllClients());
    }

    @POST
    @Path("/")
    public Response createNewClient (ClientDTO clientDTO) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        clientBean.create(
                clientDTO.getUsername(),
                clientDTO.getPassword(),
                clientDTO.getName(),
                clientDTO.getEmail()
        );
        Client client = clientBean.find(clientDTO.getUsername());
        return Response.status(Response.Status.CREATED).entity(dtoConverter.clientToDTO(client)).build();
    }

    @GET
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response get(@PathParam("username") String username) {
//        var principal = securityContext.getUserPrincipal();
//        if(!principal.getName().equals(username)) {
//            return Response.status(Response.Status.FORBIDDEN).build();
//        }
        var client = clientBean.find(username);
        if (client == null) {
            var errorMsg = "Client '%s' not found.".formatted(username);
            var status = Response.Status.NOT_FOUND;
            return Response.status(status).entity(errorMsg).build();
        }
        return Response.ok(dtoConverter.clientToDTO(client)).build();
    }

    @GET
    @Path("{username}/orders")
    public Response getClientOrders(@PathParam("username") String username) {
        if(username.equals("anonymous"))
            return Response.status(Response.Status.NOT_FOUND).entity("You are not logged in. Please login.").build();
        else
        {
            var client = clientBean.getClientOrders(username);
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
    public Response getClientOrders(@PathParam("username") String username, @PathParam("index") int index) {
        var client = clientBean.getClientOrders(username);
        var orders = client.getOrders();
        return Response.ok(dtoConverter.orderToDTO(orders.get(index))).build();
    }

//    @GET
//    @Path("{username}/alerts")
//    public Response getClientAlerts(@PathParam("username") String username) {
//        var client = clientBean.getClientAlerts(username);
//        var dtos = toDTOsAlert(client.getAlerts());
//        return Response.ok(dtos).build();
//    }



}
