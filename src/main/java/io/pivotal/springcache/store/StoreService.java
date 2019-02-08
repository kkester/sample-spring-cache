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

    public Store getStoreResource() {
        Store.StoreBuilder builder = Store.builder();
        if (offerService != null) {
            builder.banners(offerService.getOffers("banners")).promotions(offerService.getOffers("promotions"));
        }
        return builder.products(productService.getProducts("all")).build();
    }
}
