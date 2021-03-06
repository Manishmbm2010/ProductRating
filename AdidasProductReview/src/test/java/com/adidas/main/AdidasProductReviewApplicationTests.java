package com.adidas.main;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.adidas.controller.ProductReviewController;

@RunWith(SpringRunner.class)
@SpringBootTest

public class AdidasProductReviewApplicationTests {

	@Autowired
	ProductReviewController productReviewController;

	@Test
	public void contextLoads() {
		assertThat(productReviewController).isNotNull();
	}

}
