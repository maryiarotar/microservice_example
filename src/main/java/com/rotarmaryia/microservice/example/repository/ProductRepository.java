package com.rotarmaryia.microservice.example.repository;

import com.rotarmaryia.microservice.example.dto.ProductDTO;
import com.rotarmaryia.microservice.example.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, Long> {

}
