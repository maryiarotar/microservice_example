package com.rotarmaryia.microservice.example.dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDTO {

    private String name;
    private String description;
    private double price;

}
