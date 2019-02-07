package io.pivotal.springcache;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.cache.config.EnableGemfireCaching;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableLogging;

@Configuration
@ClientCacheApplication(name = "spring-cache-example")
@EnableGemfireCaching
@EnableEntityDefinedRegions(basePackages = {"com.pivotal.springcache"})
@EnableLogging(logLevel = "info")
public class SpringCacheConfig {

}
