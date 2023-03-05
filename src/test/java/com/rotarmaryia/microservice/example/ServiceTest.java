package com.rotarmaryia.microservice.example;

import com.rotarmaryia.microservice.example.dto.ProductDTO;
import com.rotarmaryia.microservice.example.model.Product;
import com.rotarmaryia.microservice.example.repository.ProductRepository;
import com.rotarmaryia.microservice.example.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void getAll(){
        List<Product> list = new ArrayList<>();
        list.add(new Product(123, "Chocolate", "wqeryt", 25.13));
        list.add(new Product(222, "uoiuo", ",mn", 25.13));;

        when(repository.findAll()).thenReturn(list);
        assertEquals(service.getAll(), list);
    }

    





}
