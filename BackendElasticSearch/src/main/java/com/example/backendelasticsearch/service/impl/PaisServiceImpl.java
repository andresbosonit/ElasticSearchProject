package com.example.backendelasticsearch.service.impl;

import com.example.backendelasticsearch.entity.Pais;
import com.example.backendelasticsearch.repo.PaisRepo;
import com.example.backendelasticsearch.repo.ProductRepo;
import com.example.backendelasticsearch.service.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaisServiceImpl implements PaisService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private PaisRepo paisRepo;


    @Override
    public Pais insertPais(Pais pais) {
        return paisRepo.save(pais);
    }

    @Override
    public Iterable<Pais> getPais() {
        return paisRepo.findAll();
    }
}
