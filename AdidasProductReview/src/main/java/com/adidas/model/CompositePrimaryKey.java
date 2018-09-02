package com.adidas.model;

import java.io.Serializable;

public class CompositePrimaryKey implements Serializable {
	String productId;
	String userId;

	public CompositePrimaryKey() {
	}

	public CompositePrimaryKey(String userId, String productId) {
		this.userId = userId;
		this.productId = productId;
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
