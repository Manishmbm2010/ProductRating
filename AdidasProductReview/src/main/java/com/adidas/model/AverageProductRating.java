package com.adidas.model;

public class AverageProductRating {

	String productId;
	long numberOfReviews;
	double averageRating;

	public AverageProductRating(String productId, double averageRating, long numberOfReviews) {
		this.productId = productId;
		this.numberOfReviews = numberOfReviews;
		this.averageRating = averageRating;
	}

	public String getProductId() {
		return productId;
	}

	public long getNumberOfReviews() {
		return numberOfReviews;
	}

	public double getAverageRating() {
		return averageRating;
	}

}
