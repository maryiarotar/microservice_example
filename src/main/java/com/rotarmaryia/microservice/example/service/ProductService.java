package com.rotarmaryia.microservice.example.service;

import com.rotarmaryia.microservice.example.dto.ProductDTO;
import com.rotarmaryia.microservice.example.model.Product;
import com.rotarmaryia.microservice.example.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void addAll(List<ProductDTO> products){
        List<Product> listToSave = new ArrayList<>();
        for (ProductDTO prod : products) {
            listToSave.add(Product.builder()
                    .name(prod.getName())
                    .description(prod.getDescription())
                    .price(prod.getPrice()).build());
        }
        repository.insert(listToSave);
    }

    public List<Product> getAll(){
        List<Product> list = repository.findAll();
        return list;
    }

    public Product getById(long id){
        Optional<Product> prod = repository.findById(id);
        if (prod.isPresent()) { return prod.get();}
        else return null;
    }

    public String update(Product product){
        Optional<Product> oldProduct = repository.findById(product.getId());
        if (oldProduct.isPresent()) {
            repository.save(product);
            return "obj with id [" + product.getId() + "] was updated. Updated rows: " +
                    product.compareTo(oldProduct.get());
        }
        return "Can't update! Product is not exist in DB!";
    }

    public String removeById(long id){
        Optional<Product> product = repository.findById(id);
        if (product.isPresent()){
            repository.deleteById(id);
            return "Product id={"+id+"} was deleted!";
        }
        return "Product id{"+id+"} was not found!";
    }


    public String removeAll(){
        if (repository.findAll().size() > 0) {
            repository.deleteAll();
            return "All products deleted!";
        } else {
            return "No products!";
        }
    }

}
