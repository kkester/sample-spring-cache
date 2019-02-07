package io.pivotal.springcache.store;

import io.pivotal.springcache.offers.OfferService;
import io.pivotal.springcache.products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    @Autowired(required=false)
    private OfferService offerService;

    @Autowired
    private ProductService productService;

    //@Cacheable(value = "storeCache")
    public Store getHomeResource() {
        Store.StoreBuilder builder = Store.builder();
        if (offerService != null) {
            builder.banners(offerService.getBanners()).promotions(offerService.getPromotions());
        }
        return builder.products(productService.getProducts()).build();
    }
}
