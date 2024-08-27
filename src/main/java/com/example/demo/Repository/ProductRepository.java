package com.example.demo.Repository;

import com.example.demo.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    @Query("{ 'name': { $regex: ?0, $options: 'i' }, 'price': { $gte: ?1, $lte: ?2 } }")
    List<Product> findByNameAndPriceRange(String name, Double minPrice, Double maxPrice);

}