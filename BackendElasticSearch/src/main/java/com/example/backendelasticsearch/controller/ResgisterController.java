package com.example.backendelasticsearch.controller;

import com.example.backendelasticsearch.entity.Person;
import com.example.backendelasticsearch.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:4200/")
public class ResgisterController {
    @Autowired
    PersonRepo personRepository;
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();
    public static String generarID(int longitud) {
        StringBuilder id = new StringBuilder(longitud);
        for (int i = 0; i < longitud; i++) {
            int indice = random.nextInt(CARACTERES.length());
            id.append(CARACTERES.charAt(indice));
        }
        return id.toString();
    }

    @PostMapping("/register")
    public ResponseEntity<Person> register(@RequestBody Map<String, String> requestMap) {
        String username = requestMap.get("username");
        String password = requestMap.get("password");
        Person person = new Person();
        person.setId(generarID(12));
        person.setUsername(username);
        person.setPassword(password);
        personRepository.save(person);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }
}
