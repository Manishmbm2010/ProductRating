package com.adidas.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import com.adidas.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	@Value("${adidas.product.uri}")
	String adidasProductUri;
	@Value("${my.product.review.uri}")
	String myProductReviewUri;
	@Value("${my.product.review.uri.timeout}")
	int myProductReviewUriTimeOut;
	@Value("${adidas.product.uri.timeout}")
	int adidasProductUriTimeOut;

	public String getProductInfo(String productId) {

		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(adidasProductUriTimeOut);
		((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(adidasProductUriTimeOut);
		Map<String, Object> result1;

		try {
			result1 = restTemplate.getForObject(adidasProductUri + productId, Map.class);
		} catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
			logger.error("APi call " + adidasProductUri + productId + " has failed , Have a look at stackTrace");
			httpClientOrServerExc.printStackTrace();
			if (HttpStatus.NOT_FOUND.equals(httpClientOrServerExc.getStatusCode())) {
				throw new NotFoundException("Product not found");
			} else {
				throw httpClientOrServerExc;
			}
		} catch (Exception e) {
			logger.error("APi call " + adidasProductUri + productId + " has failed , Have a look at stackTrace");
			e.printStackTrace();
			throw e;
		}

		((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory())
				.setConnectTimeout(myProductReviewUriTimeOut);
		((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(myProductReviewUriTimeOut);
		try {
			Map<String, Object> result2 = restTemplate.getForObject(myProductReviewUri + productId, Map.class);
			for (Map.Entry<String, Object> record : result2.entrySet()) {
				if (record.getKey().equalsIgnoreCase("productId"))
					continue;
				result1.put(record.getKey(), record.getValue());
			}
		} catch (Exception e) {
			logger.error("APi call " + myProductReviewUri + productId + " has failed , Have a look at stackTrace");
			e.printStackTrace();
			logger.error("Showing result only for main adidas product call");
		}

		try {
			String response = mapper.writeValueAsString(result1);
			logger.debug(response);
			return response;
		} catch (JsonProcessingException e) {
			logger.error("Error in converting Object to Json");
			throw new com.adidas.exception.JsonProcessingException("Not able to parse response object into json");
		}

	}
}
