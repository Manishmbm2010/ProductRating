package com.adidas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.adidas.model.ProductReview;
import com.adidas.repository.ProductReviewRepository;

@Service
public class CacheService {

	@Autowired
	ProductReviewRepository productReviewRepository;

	@Cacheable(value = "product-user-review", key = "{ #userId,#productId }", unless = "#result == null")
	public ProductReview findUserRatingForProduct(String userId, String productId) {
		return productReviewRepository.findUserRatingForProduct(userId, productId);
	}
}
