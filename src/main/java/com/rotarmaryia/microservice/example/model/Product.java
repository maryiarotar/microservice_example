package com.rotarmaryia.microservice.example.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Document(value = "product")
public class Product {

    @Transient
    public static final String SEQUENCE_NAME = "product_sequence";

    @Id
    private long id;
    private String name;
    private String description;
    private double price;

    @Transient
    public int compareTo(Product other){
        int counter = 0;
        counter += (this.id == other.id) ? 0 : 1;
        counter += (this.name.compareTo(other.name)==0) ? 0 : 1;
        counter += (this.description.compareTo(other.description)==0) ? 0 : 1;
        counter += (Double.compare(this.price, other.price)==0) ? 0 : 1;
        return counter;
    }

}
