package com.rotarmaryia.microservice.example;

import com.rotarmaryia.microservice.example.dto.ProductDTO;
import com.rotarmaryia.microservice.example.model.Product;
import com.rotarmaryia.microservice.example.repository.ProductRepository;
import com.rotarmaryia.microservice.example.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    private ProductRepository repository;

    private ProductService service;

    @BeforeEach
    void setup(){
        service = new ProductService(repository);
    }


    @Captor
    private ArgumentCaptor<ProductDTO> productCaptor = ArgumentCaptor.forClass(ProductDTO.class);


    @Test
    void saveProductTest(){
        ProductDTO dto = new ProductDTO("Chocolate", "wqeryt", 25.13);
        Product saved = new Product(123, "Chocolate", "wqeryt", 25.13);

        when(repository.insert(any(Product.class))).thenReturn(saved);

        assertEquals(service.add(dto), saved);
    }

}
