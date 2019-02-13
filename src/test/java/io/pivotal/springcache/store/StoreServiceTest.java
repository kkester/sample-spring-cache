package io.pivotal.springcache.store;

import io.pivotal.springcache.offers.Offer;
import io.pivotal.springcache.offers.OfferService;
import io.pivotal.springcache.products.Product;
import io.pivotal.springcache.products.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    private StoreService subject;

    private StoreService subjectOffersEnabled;

    @Mock
    private OfferService offerService;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        subject = new StoreService(Optional.of(offerService), productService);
        subjectOffersEnabled = new StoreService(Optional.empty(), productService);
    }

    @Tag("unit")
    @Test
    public void getStoreResource_WhenOffersEnabled() {

        // given
        Offer banner = new Offer();
        doReturn(Arrays.asList(banner)).when(offerService).getOffers("banners");

        Offer promo = new Offer();
        doReturn(Arrays.asList(banner)).when(offerService).getOffers("promotions");

        Product product = new Product();
        given(productService.getProducts("all")).willReturn(Arrays.asList(product));

        // when
        Store storeResource = subject.getStoreResource();

        // then
        assertThat(storeResource).isNotNull();
        assertThat(storeResource.getBanners()).contains(banner);
        assertThat(storeResource.getPromotions()).contains(promo);
        assertThat(storeResource.getProducts()).contains(product);
    }

    @Tag("unit")
    @Test
    public void getStoreResource_WhenOffersDisabled() {

        // given
        Product product = new Product();
        given(productService.getProducts("all")).willReturn(Arrays.asList(product));

        // when
        Store storeResource = subjectOffersEnabled.getStoreResource();

        // then
        assertThat(storeResource).isNotNull();
        assertThat(storeResource.getBanners()).isNull();
        assertThat(storeResource.getPromotions()).isNull();
        assertThat(storeResource.getProducts()).contains(product);
    }
}