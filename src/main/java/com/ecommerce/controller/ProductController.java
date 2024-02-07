package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;

@RestController(value = "/product")
public class ProductController {

    @Autowired // Significa que la instancia será inyectada por Spring automáticamente
    private ProductRepository productRepository;

    @GetMapping(value = "/products")
    public List<Product> getProducts() {
        // return Arrays.asList("iPhone", "Samsung", "Nokia");
        return productRepository.findAll();

    }
}
