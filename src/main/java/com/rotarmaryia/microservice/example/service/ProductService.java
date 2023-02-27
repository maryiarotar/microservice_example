package com.rotarmaryia.microservice.example.service;

import com.rotarmaryia.microservice.example.dto.ProductDTO;
import com.rotarmaryia.microservice.example.model.Product;
import com.rotarmaryia.microservice.example.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    //@Autowired
    private final ProductRepository repository;


    public Product add(ProductDTO productDto){
        Product product = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice()).build();
        return repository.insert(product);
    }

    public List<Product> getAll(){
        List<Product> list = repository.findAll();
        return list;
    }


}
