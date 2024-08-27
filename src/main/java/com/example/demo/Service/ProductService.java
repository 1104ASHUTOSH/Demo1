package com.example.demo.Service;

import com.example.demo.Exception.ProductNotFoundException;
import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(String productId, Product updatedProduct) {
        Product product = getProductById(productId);
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setAvailabilityStatus(updatedProduct.getAvailabilityStatus());
        return productRepository.save(product);
    }

    public void deleteProduct(String productId) {
        Product product = getProductById(productId);
        productRepository.delete(product);
    }

    public List<Product> getFilteredProducts(String name, Double minPrice, Double maxPrice) {
        if (name != null && !name.isEmpty() && minPrice != null && maxPrice != null) {
            return productRepository.findByNameAndPriceRange(name, minPrice, maxPrice);
        } else if (name != null && !name.isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(name);
        } else if (minPrice != null && maxPrice != null) {
            return productRepository.findByPriceBetween(minPrice, maxPrice);
        } else {
            return productRepository.findAll();
        }
    }
}