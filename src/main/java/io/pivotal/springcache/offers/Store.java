package io.pivotal.springcache.offers;

import io.pivotal.springcache.products.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    private Collection<Offer> banners;
    private Collection<Offer> promotions;
    private Collection<Product> products;

}
