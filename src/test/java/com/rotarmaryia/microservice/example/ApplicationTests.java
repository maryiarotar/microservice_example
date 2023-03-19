package com.rotarmaryia.microservice.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rotarmaryia.microservice.example.dto.ProductDTO;
import com.rotarmaryia.microservice.example.model.Product;
import com.rotarmaryia.microservice.example.repository.ProductRepository;
import com.rotarmaryia.microservice.example.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ApplicationTests {

	@Container
	static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.2.23"));

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ProductRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	@DynamicPropertySource
	static void setup(DynamicPropertyRegistry propertyRegistry){
		propertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void createdControllerTest() throws Exception{

		ProductDTO request = getProductDto();
		String requestString = objectMapper.writeValueAsString(request);

		mockMvc.perform(post("/main/product/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestString))
				.andExpectAll(
						status().isCreated()
				);
		Assertions.assertEquals(1, repository.findAll().size());
	}

	@Test
	void getAllProductsTest() throws Exception{

		List<Product> request = new ArrayList<>();
		request.add(getProduct());
		request.add(getProduct());
		request.get(1).setId(1002l);
		String response = objectMapper.writeValueAsString(request);

		repository.insert(request);

		mockMvc.perform(get("/main/product"))
				.andExpectAll(
						status().isOk(),
						content().contentType(MediaType.APPLICATION_JSON),
						content().string(response)
				);
	}

	@Test
	void getProductByIdTest() throws Exception {

		repository.insert(getProduct());
		String response = objectMapper.writeValueAsString(getProduct());

		mockMvc.perform(get("/main/product/1001"))
				.andExpectAll(
						content().contentType(MediaType.APPLICATION_JSON),
						content().string(response),
						status().isOk()
				);
	}


	@Test
	void updateProductPositiveTest() throws Exception {

		Product oldProduct = getProduct();
		Product product = getProduct();
		product.setName("brand new product");

		String response = "obj with id [" + product.getId() + "] was updated. Updated rows: " +
						product.compareTo(oldProduct);

		String request = objectMapper.writeValueAsString(product);

		repository.insert(oldProduct);

		mockMvc.perform(post("/main/product/edit")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
				.andExpectAll(
						status().isOk(),
						content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")),
						content().string(response)
				);
	}


	@Test
	void updateProductNegativeTest() throws Exception {

		Product oldProduct = getProduct();
		Product product = getProduct();
		product.setId(2l);

		String response = "Can't update! Product is not exist in DB!";

		String request = objectMapper.writeValueAsString(product);

		repository.insert(oldProduct);

		mockMvc.perform(post("/main/product/edit")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request))
				.andExpectAll(
						status().isOk(),
						content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")),
						content().string(response)
				);
	}

	@Test
	void updateProductWithParamsTest() throws Exception {

		Product oldProduct = getProduct();
		Product product = getProduct();
		product.setName("something");

		String response = "obj with id [" + product.getId() + "] was updated. Updated rows: " +
				product.compareTo(oldProduct);

		repository.insert(oldProduct);

		mockMvc.perform(post("/main/product/edit/1001?name=something"))
				.andExpectAll(
						status().isOk(),
						content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")),
						content().string(response)
				);
	}

	@Test
	void deleteTest() throws Exception {

		repository.insert(getProduct());

		mockMvc.perform(post("/main/product/remove/1001"))
				.andExpect(status().isOk());

		Assertions.assertEquals(0, repository.findAll().size());
	}

	private ProductDTO getProductDto(){
		return ProductDTO.builder()
				.name("Watermelon")
				.description("A good and tasty one")
				.price(99.99)
				.build();
	}

	private Product getProduct(){
		return Product.builder()
				.id(1001l)
				.name("Watermelon")
				.description("A good and tasty one")
				.price(99.99)
				.build();
	}


}
