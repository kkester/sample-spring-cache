package io.pivotal.springcache.offers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.gemfire.mapping.annotation.Region;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Region(name = "OffersCache")
@ConditionalOnProperty(prefix = "feature.toggle", name = "offers-enabled", havingValue="true")
@Slf4j
public class OfferService {

    @Value("${integration.offers.base-url}")
    private String offersBaseUrl;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public Collection<Offer> getBanners() {

        return this.getOffers("banners");
    }

    public Collection<Offer> getPromotions() {

        return this.getOffers("promotions");
    }

    @Cacheable
    public Collection<Offer> getOffers(String type) {

        Collection<Offer> offers = Collections.emptyList();
        RestTemplate restTemplate = restTemplateBuilder.build();

        try {
            ResponseEntity<List<Offer>> response = restTemplate.exchange(
                    offersBaseUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Offer>>() {},
                    type
            );
            offers = response.getBody();
        } catch (Exception e) {
            log.error("Unexpected error occurred invoking call to get offers {}", e);
        }

        return offers;
    }
}
