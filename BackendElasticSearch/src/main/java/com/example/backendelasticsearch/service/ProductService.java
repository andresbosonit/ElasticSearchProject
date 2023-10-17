package com.example.backendelasticsearch.service;

import com.example.backendelasticsearch.entity.Product;


public interface ProductService {
    Product insertProduct(Product product, String[] paises);

    Iterable<Product> getProduct();

}
