package io.pivotal.springcache.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    private String id;
    private String name;
    private String sku;
    private LocalDate releaseDate;
    private Double price;
    private String currency;
    private String description;
    private String starRating;
    private String imageUrl;

}
