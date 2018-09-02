package com.adidas.main;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.adidas.model.ProductReview;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
// @WebMvcTest
public class Tests {

	@Autowired
	MockMvc mockMvc;
	private ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testReturn201ForNewProductReview() throws Exception {
		ProductReview productReview = new ProductReview("Manish9", "M20324", 4.5);
		String jsonProductReview = mapper.writeValueAsString(productReview);
		mockMvc.perform(post("/review").with(httpBasic("manish", "manish1$3")).contentType(MediaType.APPLICATION_JSON)
				.content(jsonProductReview)).andExpect(status().isCreated());

	}

	@Test
	public void testReturn401AccessDenied() throws Exception {
		ProductReview productReview = new ProductReview("Manish9", "M20324", 4.5);
		String jsonProductReview = mapper.writeValueAsString(productReview);
		mockMvc.perform(post("/review").contentType(MediaType.APPLICATION_JSON).content(jsonProductReview))
				.andExpect(status().isUnauthorized());

	}

	@Test
	public void testUpdateProductReviewPositive() throws Exception {
		ProductReview productReview = new ProductReview("Manish9", "M20324", 4.5);
		String jsonProductReview = mapper.writeValueAsString(productReview);
		mockMvc.perform(post("/review").with(httpBasic("manish", "manish1$3")).contentType(MediaType.APPLICATION_JSON)
				.content(jsonProductReview)).andExpect(status().isCreated());
		
		productReview = new ProductReview("", "", 4.5);
		jsonProductReview = mapper.writeValueAsString(productReview);
		mockMvc.perform(patch("/review/Manish9/M20324").with(httpBasic("manish", "manish1$3"))
				.contentType(MediaType.APPLICATION_JSON).content(jsonProductReview))
				.andExpect(status().isOk());

	}
	
	@Test
	public void testUpdateProductReviewNegative() throws Exception {
		ProductReview productReview = new ProductReview("", "", 4.5);
		String jsonProductReview = mapper.writeValueAsString(productReview);
		mockMvc.perform(patch("/review/Manish9/M20324").with(httpBasic("manish", "manish1$3"))
				.contentType(MediaType.APPLICATION_JSON).content(jsonProductReview))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testGetProductReview() throws Exception {

		// Rating 1
		ProductReview productReview = new ProductReview("Manish9", "X20324", 4.5);
		String jsonProductReview = mapper.writeValueAsString(productReview);
		mockMvc.perform(post("/review").with(httpBasic("manish", "manish1$3")).contentType(MediaType.APPLICATION_JSON)
				.content(jsonProductReview)).andExpect(status().isCreated());

		// Rating 2
		productReview = new ProductReview("Manish9_1", "X20324", 3.5);
		jsonProductReview = mapper.writeValueAsString(productReview);
		mockMvc.perform(post("/review").with(httpBasic("manish", "manish1$3")).contentType(MediaType.APPLICATION_JSON)
				.content(jsonProductReview)).andExpect(status().isCreated());

		mockMvc.perform(get("/review/X20324").contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.productId", is("X20324"))).andExpect(jsonPath("$.numberOfReviews", is(2)))
				.andExpect(jsonPath("$.averageRating", is(4.0))).andExpect(status().is(200));
	}

	public void testDeleteProductReviewForUser() throws Exception {

		ProductReview productReview = new ProductReview("Manish9", "X20324", 4.5);
		String jsonProductReview = mapper.writeValueAsString(productReview);
		mockMvc.perform(post("/review").with(httpBasic("manish", "manish1$3")).contentType(MediaType.APPLICATION_JSON)
				.content(jsonProductReview)).andExpect(status().isCreated());
		mockMvc.perform(delete("/review/Manish9/X20324").contentType("application/json;charset=UTF-8"))
				.andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(status().isOk());
	}

}
