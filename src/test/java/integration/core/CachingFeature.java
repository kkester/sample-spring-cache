package integration.core;

import io.pivotal.springcache.CloudCacheConfig;
import io.pivotal.springcache.products.Product;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Import(CloudCacheConfig.class)
@ConditionalOnProperty(prefix = "feature.toggle", name = "caching-enabled", havingValue="true")
public class CachingFeature {

    @Autowired
    private GemFireCache gemFireCache;

    public List<Product> getCacheValue(String region, String key) {
        Region<Object, Object> productsRegion = gemFireCache.getRegion(region);
        return productsRegion == null ? null : (List<Product>) productsRegion.get(key);
    }
}
