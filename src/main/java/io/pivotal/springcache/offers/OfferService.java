package io.pivotal.springcache.offers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;

@Service
@ConditionalOnProperty(prefix = "feature.toggle", name = "offers-enabled", havingValue="true")
public class OfferService {

    @Value("${integration.offers.base.url}")
    private String offersBaseUrl;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public Collection<Offer> getBanners() {

        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<List<Offer>> response = restTemplate.exchange(
                offersBaseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Offer>>(){},
                "banners"
        );

        return response.getBody();
    }

    public Collection<Offer> getPromotions() {

        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<List<Offer>> response = restTemplate.exchange(
                offersBaseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Offer>>(){},
                "promotions"
        );

        return response.getBody();
    }
}
