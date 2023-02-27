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
    @PostMapping("create")
    public void createProduct(@RequestBody ProductDTO productDto){
        service.add(productDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Product> getAllProducts(){
        return service.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value="/{id}")
    public Product getProductById(@PathVariable("id") long id){
        return service.getById(id);
    }

    @PostMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    public String updateProduct(@RequestBody Product product){
        return service.update(product);
    }

    @PostMapping(value={"/edit/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public String updateById(@PathVariable(value="id") Long id,
                             @RequestParam(value="name", required=false) String name,
                             @RequestParam(value="description", required=false) String description,
                            @RequestParam(value="price", required=false) Double price){
        Product old = service.getById(id);
        Product newProd = old.toBuilder().build();
        if (name != null) newProd.setName(name);
        if (description != null) newProd.setDescription(description);
        if (price != null) newProd.setPrice(price);
        return service.update(newProd);
    }






}
