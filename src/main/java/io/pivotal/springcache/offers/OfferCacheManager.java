package io.pivotal.springcache.offers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "feature.toggle", name = "offers-enabled", havingValue = "true")
@Slf4j
public class OfferCacheManager {

    @Autowired
    private CacheManager cacheManager;

    @Scheduled(fixedRate = (1000 * 60))
    public void evictOfferCaches() {

        log.info("Flushing Offer Cache");

        cacheManager.getCache("offers").evict("banners");
        cacheManager.getCache("offers").evict("promotions");
    }
}
