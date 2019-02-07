package io.pivotal.springcache.products;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductEntityRepository productRepository;

    public ProductService(ProductEntityRepository productRepository) {
        this.productRepository = productRepository;
    }

    /*@Cacheable(value = "productsCache")*/
    public Collection<Product> getProducts() {
        return productRepository.findAll().stream().map(this::map).collect(Collectors.toList());
    }


    /*@Caching(
        put = {
            @CachePut(value = "userCache", key = "'username:' + #result.username", condition = "#result != null"),
            @CachePut(value = "userCache", key = "#result.id", condition = "#result != null")
        }
    )*/
    public Optional<Product> getProductById(String productId) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);
        Product product = productEntityOptional.isPresent() ? this.map(productEntityOptional.get()) : null;
        return Optional.of(product);
    }

    public void deleteProductById(String productId) {
        productRepository.deleteById(productId);
    }

    public Product save(Product product) {
        ProductEntity productEntity = this.map(product);
        productEntity.setProductId(UUID.randomUUID().toString());
        productRepository.save(productEntity);
        return this.map(productEntity);
    }

    private Product map(ProductEntity productEntity) {
        return Product.builder()
                .id(productEntity.getProductId())
                .name(productEntity.getProductName())
                .description(productEntity.getDescription())
                .currency(productEntity.getCurrency())
                .imageUrl(productEntity.getImageUrl())
                .price(productEntity.getPrice())
                .sku(productEntity.getProductCode())
                .starRating(productEntity.getStarRating())
                .releaseDate(productEntity.getReleaseDate())
                .build();
    }

    private ProductEntity map(Product product) {

        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setCurrency(product.getCurrency());
        productEntity.setImageUrl(product.getImageUrl());
        productEntity.setPrice(product.getPrice());
        productEntity.setProductCode(product.getSku());
        productEntity.setReleaseDate(product.getReleaseDate());
        productEntity.setStarRating(product.getStarRating());
        return productEntity;
    }
}
