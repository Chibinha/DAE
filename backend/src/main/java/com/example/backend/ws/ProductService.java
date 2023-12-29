package com.example.backend.ws;

import com.example.backend.dtos.ProductDTO;
import com.example.backend.entities.Product;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Path;
import com.example.backend.ejbs.ProductBean;

@Path("/products")

public class ProductService {
    @EJB
    private ProductBean productBean;

    private ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getProduct_id(),
                product.getName(),
                product.getDescription(),
                product.getWeight(),
                product.getIngredients()
        );
    }
}
