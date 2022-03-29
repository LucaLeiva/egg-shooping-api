package com.globant.training.eggshoopingv1.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCTS")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private int ammountInStock;

    Product(){}

    public Product(String name, String description, float price, int ammountInStock){
        this.name = name;
        this.description = description;
        this.price = price;
        this.ammountInStock = ammountInStock;
    }
}
