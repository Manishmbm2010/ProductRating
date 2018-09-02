package com.adidas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adidas.model.AverageProductRating;
import com.adidas.model.ProductReview;
import com.adidas.service.ProductReviewService;

@RestController
public class ProductReviewController {

	private static final Logger logger = LoggerFactory.getLogger(ProductReviewController.class);
	@Autowired
	ProductReviewService productReviewService;

	@Cacheable(value = "product-review", key = "#productId", unless = "#result == null")
	@GetMapping("/review/{productId}")
	public ResponseEntity<AverageProductRating> getProductReview(@PathVariable String productId) {
		AverageProductRating productRating = productReviewService.getProductReview(productId);
		return new ResponseEntity<>(productRating, HttpStatus.OK);
	}

	@CacheEvict(value = "product-review", key = "#productReview.productId")
	@PostMapping("/review")
	public ResponseEntity<String> addProductReview(@RequestBody ProductReview productReview) {
		productReviewService.addProductReview(productReview);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@CacheEvict(value = "product-review", key = "#productId")
	@PatchMapping("/review/{userId}/{productId}")
	public ResponseEntity<ProductReview> updateProductReview(@RequestBody ProductReview productReview,
			@PathVariable String userId, @PathVariable String productId) {
		ProductReview userReview = productReviewService.updateProductReview(userId,productId,productReview);
		return new ResponseEntity<>(userReview,HttpStatus.OK);
	}

	@Caching(evict = { @CacheEvict(value = "product-review", key = "#productId"),
			@CacheEvict(value = "product-user-review", key = "{#userId,#productId }") })
	@DeleteMapping("/review/{userId}/{productId}")
	public ResponseEntity<String> deleteProductReview(@PathVariable String userId, @PathVariable String productId) {
		productReviewService.deleteReview(userId, productId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
