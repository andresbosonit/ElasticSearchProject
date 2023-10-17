package com.example.backendelasticsearch.service;

import com.example.backendelasticsearch.entity.Pais;

public interface PaisService {
    Pais insertPais(Pais pais);

    Iterable<Pais> getPais();
}
