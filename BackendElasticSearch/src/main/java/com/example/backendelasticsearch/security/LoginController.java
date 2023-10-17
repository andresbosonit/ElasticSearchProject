package com.example.backendelasticsearch.security;


import com.example.backendelasticsearch.entity.Person;
import com.example.backendelasticsearch.entity.Token;
import com.example.backendelasticsearch.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:4200/")
public class LoginController {
    @Autowired
    PersonRepo personRepository;
    @Autowired
    JwtToken jwtToken;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody Map<String, String> requestMap) {
        String username = requestMap.get("username");
        String password = requestMap.get("password");

        Person person = personRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("No se encontró la persona con nombre de usuario: " + username));

        if (!person.getPassword().equals(password)) {
            throw new RuntimeException("Las contraseñas no coinciden");
        }

        return new ResponseEntity<>(jwtToken.generateToken(username, "ROLE_USER"), HttpStatus.OK);
    }
}
