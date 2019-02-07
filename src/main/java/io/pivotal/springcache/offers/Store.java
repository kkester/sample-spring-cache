package io.pivotal.springcache.offers;

import io.pivotal.springcache.products.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Region(name = "HomeCache")
public class Store {

    private Collection<Offer> banners;
    private Collection<Offer> promotions;
    private Collection<Product> products;

}
