package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Hereda de la clase de spring que permite realizar todas las operaciones CRUD

    List<Product> findByName(String name, Pageable pageable);

    List<Product> findByNameLike(String name, Pageable pageable);

    List<Product> findByPriceGreaterThan(Float price); //also can use greaterThanEqual 

    List<Product> findByPriceLessThan(Float price); //also can use lessThanEqual 

    List<Product> findByPriceBetween(Float price1, Float price2);

    List<Product> findByNameAndDescription(String name, String description);

    List<Product> findByNameOrDescription(String name, String description);

    List<Product> findByNameOrderByName(String name);

    List<Product> findByNameOrderByNameDesc(String name);

    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);


}
