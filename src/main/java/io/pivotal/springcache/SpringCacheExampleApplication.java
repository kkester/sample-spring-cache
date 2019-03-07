package io.pivotal.springcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class SpringCacheExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCacheExampleApplication.class, args);
	}

}