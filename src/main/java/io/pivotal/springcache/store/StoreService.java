package io.pivotal.springcache.store;

import io.pivotal.springcache.offers.OfferService;
import io.pivotal.springcache.products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StoreService {

    private Optional<OfferService> offerService;

    private ProductService productService;

    public StoreService(Optional<OfferService> offerService, ProductService productService) {
        this.offerService = offerService;
        this.productService = productService;
    }

    public Store getStoreResource() {
        Store.StoreBuilder builder = Store.builder();
        offerService.ifPresent(os -> {
            builder.banners(os.getOffers("banners")).promotions(os.getOffers("promotions"));
        });
        return builder.products(productService.getProducts("all")).build();
    }
}
