package com.ctu.bookstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;

//@Entity
public class Product {
    @Id
    String id;
    String nameProduct;
    Double importPrice;
    Double sellingPrice;
    Double salePrice;
    int quantity;
    List<String> images;



}
