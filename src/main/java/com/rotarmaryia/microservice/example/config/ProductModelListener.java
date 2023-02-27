package com.rotarmaryia.microservice.example.config;

import com.rotarmaryia.microservice.example.model.Product;
import com.rotarmaryia.microservice.example.service.DatabaseSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class ProductModelListener extends AbstractMongoEventListener<Product> {

    @Autowired
    private DatabaseSequenceService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Product> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Product.SEQUENCE_NAME));
        }
    }
}
