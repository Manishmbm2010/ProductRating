package com.adidas.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.adidas.exception.IncorrectDataInRequest;
import com.adidas.exception.NotFoundException;
import com.adidas.model.AverageProductRating;
import com.adidas.model.CompositePrimaryKey;
import com.adidas.model.ProductReview;
import com.adidas.repository.ProductReviewRepository;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProductReviewService {

	@Autowired
	ProductReviewRepository productReviewRepository;
	
	@Autowired
	CacheService cacheService;

	public void addProductReview(ProductReview productReview) {
		try {
			validateInputDataforAddReviewRequest(productReview);
			productReviewRepository.save(productReview);
		} catch (Exception e) {
			throw e;
		}
	}

	public ProductReview updateProductReview(String userId, String productId, ProductReview productReview) {

		ProductReview userReview = cacheService.findUserRatingForProduct(userId, productId);
		if (userReview == null) {
			throw new NotFoundException(
					"User " + userId + " is not have any rating information for product " + productId);
		}
		validateInputDataforUpdateReviewRequest(productReview);
		userReview.setRating(productReview.getRating());
		try {
			productReviewRepository.save(userReview);
			return userReview;
		} catch (Exception e) {
			throw e;
		}
	}

	public AverageProductRating getProductReview(String productId) {
		AverageProductRating averagProductRating = productReviewRepository.findByProductId(productId);
		if (averagProductRating == null) {
			averagProductRating = new AverageProductRating(productId, 0, 0);
		}
		return averagProductRating;
	}

	public void deleteReview(String userId, String productId) {
		try {
			productReviewRepository.deleteById(new CompositePrimaryKey(userId, productId));
		} catch (Exception e) {
			throw new NotFoundException(
					"User " + userId + " is not have any rating information for product " + productId);
		}
	}

	

	void validateInputDataforAddReviewRequest(ProductReview productReview) {
		String productId = productReview.getProductId();
		String userId = productReview.getUserId();
		double rating = productReview.getRating();
		if (productId == null && userId == null)
			throw new IncorrectDataInRequest("UserId and ProductId is mandatory data for request");
		else if (productId == null)
			throw new IncorrectDataInRequest("ProductId is mandatory data for request");
		else if (userId == null)
			throw new IncorrectDataInRequest("UserId is mandatory data for request");
		else if (rating < 0 || rating > 5) {
			throw new IncorrectDataInRequest("Rating should be in between 0 to 5");
		}
		// return true;
	}

	void validateInputDataforUpdateReviewRequest(ProductReview productReview) {
		double rating = productReview.getRating();
		if (rating < 0 || rating > 5) {
			throw new IncorrectDataInRequest("Rating should be in between 0 to 5");
		}
	}
}
