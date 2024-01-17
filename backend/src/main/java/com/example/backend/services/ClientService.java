package com.example.backend.services;

import com.example.backend.dtos.AlertDTO;
import com.example.backend.dtos.ClientDTO;
import com.example.backend.dtos.OrderDTO;
import com.example.backend.ejbs.ClientBean;
import com.example.backend.entities.Client;
import com.example.backend.entities.Order;
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
    private ExternalContext securityContext;

    private ClientDTO toDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO(
                client.getUsername(),
                client.getPassword(),
                client.getName(),
                client.getEmail()
        );
        return clientDTO;
    }

    // converts an entire list of entities into a list of DTOs
    private List<ClientDTO> toDTOsClient(List<Client> clients) {
        return clients.stream().map(this::toDTO).collect(Collectors.toList());
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
    @Path("/all") // means: the relative url path is “/api/clients/”
    public List<ClientDTO> getAllClients() {
        return toDTOsClient(clientBean.getAllClients());
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
        return Response.status(Response.Status.CREATED).entity(toDTO(client)).build();
    }

    @GET
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response get(@PathParam("username") String username) {
//        var principal = securityContext.getUserPrincipal();
//        if(!principal.getName().equals(username)) {
//            return Response.status(Response.Status.FORBIDDEN).build();
//        }
        var entity = clientBean.find(username);
        if (entity == null) {
            var errorMsg = "Client '%s' not found.".formatted(username);
            var status = Response.Status.NOT_FOUND;
            return Response.status(status).entity(errorMsg).build();
        }
        var dto = toDTO(entity);
        return Response.ok(dto).build();
    }

    @GET
    @Path("{username}/orders")
    public Response getClientOrders(@PathParam("username") String username) {
        var client = clientBean.getClientOrders(username);
        var dtos = toDTOsOrder(client.getOrders());
        return Response.ok(dtos).build();
    }

//    @GET
//    @Path("{username}/alerts")
//    public Response getClientAlerts(@PathParam("username") String username) {
//        var client = clientBean.getClientAlerts(username);
//        var dtos = toDTOsAlert(client.getAlerts());
//        return Response.ok(dtos).build();
//    }



}
