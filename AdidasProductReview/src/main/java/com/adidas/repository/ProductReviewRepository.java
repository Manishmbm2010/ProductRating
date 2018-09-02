package com.adidas.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.adidas.model.AverageProductRating;
import com.adidas.model.CompositePrimaryKey;
import com.adidas.model.ProductReview;

@Component

public interface ProductReviewRepository extends CrudRepository<ProductReview, CompositePrimaryKey> {

	@Query("Select new com.adidas.model.AverageProductRating (productId as product_id ,avg(productRating) as average_rating, count(*) as number_of_reviews) from ProductReview r where productId = :productId group by productId")
	AverageProductRating findByProductId(@Param("productId") String productId);

	@Query("Select new com.adidas.model.ProductReview (userId , productId  ,productRating ) from ProductReview r where userId = :userId and productId = :productId")
	ProductReview findUserRatingForProduct(@Param("userId") String userId, @Param("productId") String productId);
}