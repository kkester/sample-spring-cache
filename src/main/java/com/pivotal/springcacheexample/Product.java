package com.pivotal.springcacheexample;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    private String productId;
    private String productName;
    private String productCode;
    private String releaseDate;
    private String price;
    private String currency;
    private String description;
    private String starRating;
    private String imageUrl;

}
