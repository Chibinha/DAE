package com.example.backend.services;

import com.example.backend.dtos.DTOConverter;
import com.example.backend.dtos.PhysicalProductDTO;
import com.example.backend.dtos.ProductDTO;
import com.example.backend.ejbs.MakerBean;
import com.example.backend.ejbs.PhysicalProductBean;
import com.example.backend.ejbs.ProductBean;
import com.example.backend.entities.Alert;
import com.example.backend.entities.Maker;
import com.example.backend.entities.PhysicalProduct;
import com.example.backend.entities.Product;
import com.example.backend.exceptions.MyConstraintViolationException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import com.example.backend.security.Hasher;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("maker/{username}")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class MakerService {
    @EJB
    private MakerBean makerBean;
    private final DTOConverter dtoConverter = new DTOConverter();

    //Get Own products
//    @GET
//    @Path("/products")
//    public List<ProductDTO> getProducts() {
//        return dtoConverter.productToDTOList(makerBean.getProducts());
//    }
//
//    //Get Own pshysical products
//    @GET
//    @Path("/physicalproducts")
//    public List<PhysicalProductDTO> getPhysicalProducts() {
//        return dtoConverter.physicalProductToDTOList(makerBean.getPhysicalProducts());
//    }
//
//    //Get Alerts
//    @GET
//    @Path("/alerts")
//    public Response getAlerts(@PathParam("username") String username) throws MyEntityNotFoundException {
//        List<Alert> alerts = makerBean.getAlerts(username);
//        return Response.ok(dtoConverter.alertToDTOList(alerts)).build();
//    }
//
//    //Create new product
//    @POST
//    @Path("/products")
//    public Response createProduct(ProductDTO productDTO, @PathParam("username") String username) throws MyEntityNotFoundException {
//        long id = makerBean.createProduct(
//                productDTO.getName(),
//                productDTO.getPrice(),
//                productDTO.getDescription(),
//                productDTO.getWeight(),
//                productDTO.getIngredients(),
//                username
//        );
//
//        if (id < 1) {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Product with id [" + id + "] not created.").build();
//        }
//        return Response.status(Response.Status.CREATED).entity("Product with id [" + id + "] created.").build();
//    }
//
//    //Create new physical product
//    @POST
//    @Path("/physicalproducts")
//    public Response createPhysicalProduct(PhysicalProductDTO physicalProductDTO, @PathParam("username") String username) throws MyEntityNotFoundException {
//        long id = makerBean.createPhysicalProduct(
//                physicalProductDTO.getSerialNumber(),
//                physicalProductDTO.getProductId()
//        );
//
//        if (id < 1) {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Physical Product with id [" + id + "] not created.").build();
//        }
//        return Response.status(Response.Status.CREATED).entity("Physical Product with id [" + id + "] created.").build();
//    }
}

//
//
//
//
//package com.example.backend.ejbs;
//
//import com.example.backend.entities.*;
//        import com.example.backend.exceptions.MyConstraintViolationException;
//import com.example.backend.exceptions.MyEntityNotFoundException;
//import com.example.backend.security.Hasher;
//import jakarta.ejb.EJB;
//import jakarta.ejb.Stateless;
//import jakarta.persistence.*;
//        import jakarta.validation.ConstraintViolationException;
//
//import java.util.List;
//
//@Stateless
//public class MakerBean {
//    @PersistenceContext
//    private EntityManager entityManager;
//    @EJB
//    private ProductBean productBean;
//    @EJB
//    private PhysicalProductBean physicalProductBean;
//    private Hasher hasher;
//
//    public boolean exists(String username) throws MyEntityNotFoundException {
//        Query query = entityManager.createQuery("SELECT COUNT(m) FROM Maker m WHERE m.username = :username");
//        query.setParameter("username", username);
//        if ((long) query.getSingleResult() > 0L) {
//            return true;
//        } else {
//            throw new MyEntityNotFoundException("Maker with username " + username + " not found");
//        }
//    }
//
//    // CRUD
//    // Create
//    public void create(String username, String password, String name, String email) throws MyEntityNotFoundException, MyConstraintViolationException, MyEntityNotFoundException {
//        if (this.find(username) == null) {
//            try {
//                hasher = new Hasher();
//                Maker maker = new Maker(username, hasher.hash(password), name, email);
//                entityManager.persist(maker);
//                entityManager.flush(); // when using Hibernate, to force it to throw a ContraintViolationException, as in the JPA specification
//                entityManager.persist(maker);
//            } catch (ConstraintViolationException e) {
//                throw new MyConstraintViolationException(e);
//            }
//        } else {
//            throw new MyEntityNotFoundException(" ERROR -  The username [" + username + "] is already in use!!!");
//        }
//    }
//
//    // Read
//    public List<Maker> getAll() {
//        return entityManager.createNamedQuery("getAllMakers", Maker.class).getResultList();
//    }
//
//    // Find
//    public Maker find(String username) throws MyEntityNotFoundException {
//        Maker maker = entityManager.find(Maker.class, username);
//        if (maker == null) {
//            throw new MyEntityNotFoundException("Maker with username " + username + " not found");
//        }
//        return maker;
//    }
//
//    // Update
//    public void update(String username, String password, String name, String email) throws MyEntityNotFoundException {
//        Maker maker = find(username);
//
//        if (password != null) {
//            hasher = new Hasher();
//            maker.setPassword(hasher.hash(password));
//        }
//        if (name != null) {
//            maker.setName(name);
//        }
//        if (email != null) {
//            maker.setEmail(email);
//        }
//
//        entityManager.merge(maker);
//    }
//
//    // Delete
//    public void delete(String username) throws MyEntityNotFoundException {
//        Maker maker = find(username);
//        entityManager.remove(maker);
//    }
//
//    // Own Products
//    public List<Product> getProducts() {
//        return entityManager.createNamedQuery("getMakerProducts", Product.class).getResultList();
//    }
//
//    public List<PhysicalProduct> getPhysicalProducts() {
//        return entityManager.createNamedQuery("getMakerPhysicalProducts", PhysicalProduct.class).getResultList();
//    }
//
//    public long createProduct(String name, double price, String description, double weight, String ingredients, String username) throws MyEntityNotFoundException {
//        return productBean.create(name, price, description, weight, ingredients, username);
//    }
//
//    public long createPhysicalProduct(String serialNumber, long productId) throws MyEntityNotFoundException {
//        long id = physicalProductBean.create(serialNumber, productId);
//        physicalProductBean.exists(id);
//        return id;
//    }
//
//    public List<Alert> getAlerts(String username) throws MyEntityNotFoundException {
//        var user = find(username);
//        return entityManager.createNamedQuery("getUserAlerts", Alert.class)
//                .setParameter("user", user)
//                .getResultList();
//    }
//}
