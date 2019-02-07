package io.pivotal.springcache;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.cache.config.EnableGemfireCaching;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableLogging;

@Configuration
@ClientCacheApplication(name = "spring-cache-example")
@EnableGemfireCaching
@EnableEntityDefinedRegions(basePackages = {"io.pivotal.springcache.offers", "io.pivotal.springcache.products"})
@EnableLogging(logLevel = "info")
public class CloudCacheConfig {
}
