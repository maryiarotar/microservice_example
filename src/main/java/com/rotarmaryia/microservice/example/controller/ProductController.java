package com.rotarmaryia.microservice.example.controller;

import com.rotarmaryia.microservice.example.dto.ProductDTO;
import com.rotarmaryia.microservice.example.model.Product;
import com.rotarmaryia.microservice.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/main/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createProduct(@RequestBody ProductDTO productDto){
        service.add(productDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Product> getAllProducts(){
        return service.getAll();
    }

//    @GetMapping
//    public ResponseEntity<List<Product>> getAllProducts() {
//        List<Product> list = service.getAll();
//        return ResponseEntity.ok(list);
//
//    }



}
