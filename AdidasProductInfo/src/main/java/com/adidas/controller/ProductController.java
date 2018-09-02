package com.adidas.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.adidas.service.ProductService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
public class ProductController {
	@GetMapping("/hello")
	String sayHello() {
		return "Hello";
	}

	@Autowired
	ProductService productService;

	@RequestMapping(value = "/product/{productId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> getProductInfo(@PathVariable String productId)
			throws JsonParseException, JsonMappingException, IOException {
		String productInfo = productService.getProductInfo(productId);
		return new ResponseEntity<String>(productInfo, HttpStatus.OK);
	}

}
