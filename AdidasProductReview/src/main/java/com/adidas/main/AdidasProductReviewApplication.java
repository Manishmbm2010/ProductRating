package com.adidas.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = { "com.adidas.config", "com.adidas.controller", "com.adidas.service", "com.adidas.model",
		"com.adidas.exception", "com.adidas.repository" })
@EnableJpaRepositories("com.adidas.repository")
@EntityScan("com.adidas.model")
@EnableCaching
@SpringBootApplication
public class AdidasProductReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdidasProductReviewApplication.class, args);
	}
}
