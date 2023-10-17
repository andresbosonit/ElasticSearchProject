package com.example.backendelasticsearch.repo;

import com.example.backendelasticsearch.entity.Pais;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PaisRepo extends ElasticsearchRepository<Pais,String> {
}
