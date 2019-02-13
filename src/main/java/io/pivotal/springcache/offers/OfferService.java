package io.pivotal.springcache.offers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@ConditionalOnProperty(prefix = "feature.toggle", name = "offers-enabled", havingValue="true")
@Slf4j
public class OfferService {

    private String offersBaseUrl;

    private RestTemplateBuilder restTemplateBuilder;

    public OfferService(@Value("${integration.offers.base-url}")String offersBaseUrl, RestTemplateBuilder restTemplateBuilder) {
        this.offersBaseUrl = offersBaseUrl;
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Cacheable(value = "offers")
    public Collection<Offer> getOffers(String type) {

        log.info("Retrieving offers from external service");

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
