package com.pivotal.springcacheexample;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String productId) {
        return productRepository.findById(productId);
    }

    public void deleteProductById(String productId) {
        productRepository.deleteById(productId);
    }

    public Product save(Product product) {
        product.setProductId(UUID.randomUUID().toString());
        productRepository.save(product);
        return product;
    }
}
