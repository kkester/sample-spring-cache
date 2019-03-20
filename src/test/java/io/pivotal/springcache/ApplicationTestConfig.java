package io.pivotal.springcache;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.GemFireCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
import org.springframework.data.gemfire.cache.GemfireCacheManager;
import org.springframework.data.gemfire.tests.mock.annotation.EnableGemFireMockObjects;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@Configuration
@EnableGemFireMockObjects
@Import(CloudCacheConfig.class)
@Slf4j
public class ApplicationTestConfig {

    @Primary
    @Bean(name="testCacheManager")
    @ConditionalOnProperty(prefix = "feature.toggle", name = "caching-enabled", havingValue="true")
    GemfireCacheManager cacheManager(GemFireCache gemfireCache) {
        GemfireCacheManager gemfireCacheManger = new GemfireCacheManager();
        gemfireCacheManger.setCache(gemfireCache);
        return gemfireCacheManger;
    }

    @Bean
    @Profile("wire")
    WireMockServer wireMockServer(@Value("${wiremock.dynamic.port}") Integer port) {
        return new WireMockServer(options().port(port));
    }

}
