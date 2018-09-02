package com.adidas.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan(basePackages = { "com.adidas.config", "com.adidas.controller", "com.adidas.service", "com.adidas.model","com.adidas.exception" })
@SpringBootApplication
public class AdidasProductInfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdidasProductInfoApplication.class, args);
	}
}
