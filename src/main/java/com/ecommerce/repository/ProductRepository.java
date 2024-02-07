package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Hereda de la clase de spring que permite realizar todas las operaciones de
    // CRUD

}
