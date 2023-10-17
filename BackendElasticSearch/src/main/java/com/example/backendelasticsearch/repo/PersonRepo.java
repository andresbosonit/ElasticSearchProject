package com.example.backendelasticsearch.repo;

import com.example.backendelasticsearch.entity.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface PersonRepo extends ElasticsearchRepository<Person,String> {
    Optional<Person> findByUsername(String Username);
}
