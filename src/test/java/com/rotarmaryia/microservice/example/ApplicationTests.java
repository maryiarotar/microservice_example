package com.rotarmaryia.microservice.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rotarmaryia.microservice.example.dto.ProductDTO;
import com.rotarmaryia.microservice.example.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

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

	}

	@Test
	void getAllProductsTest() throws Exception{

		ProductDTO request = getProductDto();

		String requestString = objectMapper.writeValueAsString(request);

		mockMvc.perform(post("/main/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestString))
				.andExpectAll(
						status().isCreated()
				);

	}

	private ProductDTO getProductDto(){
		return ProductDTO.builder()
				.name("Watermelon")
				.description("A good and tasty one")
				.price(99.99)
				.build();
	}

}
