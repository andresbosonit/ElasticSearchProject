package com.example.backendelasticsearch.service.impl;

import com.example.backendelasticsearch.entity.Pais;
import com.example.backendelasticsearch.entity.Product;
import com.example.backendelasticsearch.repo.PaisRepo;
import com.example.backendelasticsearch.repo.ProductRepo;
import com.example.backendelasticsearch.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private PaisRepo paisRepo;
    int i = 10;
    @Override
    public Product insertProduct(Product product, String[] paises) {
        for(String pais: paises){
            paisRepo.findById(pais).orElseThrow();
        }
        for(String pais: paises){
            Pais paisEncontrado =paisRepo.findById(pais).orElseThrow();
            paisEncontrado.getProductos().add(product);
            paisRepo.save(paisEncontrado);
        }
        return productRepo.save(product);
    }

    @Override
    public Iterable<Product> getProduct() {
        return productRepo.findAll();
    }

}
