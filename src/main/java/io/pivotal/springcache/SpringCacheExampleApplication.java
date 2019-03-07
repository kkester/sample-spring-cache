package io.pivotal.springcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCircuitBreaker
@EnableScheduling
public class SpringCacheExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCacheExampleApplication.class, args);
	}

}