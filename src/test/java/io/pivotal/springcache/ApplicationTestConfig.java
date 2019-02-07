package io.pivotal.springcache;

import org.apache.geode.cache.GemFireCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.gemfire.cache.GemfireCacheManager;
import org.springframework.data.gemfire.tests.mock.annotation.EnableGemFireMockObjects;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@EnableGemFireMockObjects
@Import(CloudCacheConfig.class)
@ActiveProfiles("test")
public class ApplicationTestConfig {

    @Primary
    @Bean(name="testCacheManager")
    GemfireCacheManager cacheManager(GemFireCache gemfireCache) {
        GemfireCacheManager gemfireCacheManger = new GemfireCacheManager();
        gemfireCacheManger.setCache(gemfireCache);
        return gemfireCacheManger;
    }

}
