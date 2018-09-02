package com.adidas.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(CompositePrimaryKey.class)
@Table(name = "product_review")
// @javax.persistence.Cacheable(false)
//@CacheEvict
public class ProductReview {

	// @EmbeddedId
	// public CompositePrimaryKey compositeKey;
	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// private Long seq;
	@Id
	String productId;
	@Id
	String userId;
	double productRating;

	public ProductReview() {

	}

	public ProductReview(String userId, String productId, double productRating) {
		this.userId = userId;
		this.productId = productId;
		this.productRating = productRating;
	}

	public double getRating() {
		return productRating;
	}

	public void setRating(double productRating) {
		this.productRating = productRating;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
