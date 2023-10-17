package com.example.backendelasticsearch.controller;

import com.example.backendelasticsearch.entity.Pais;
import com.example.backendelasticsearch.service.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pais")
public class PaisController {
    @Autowired
    private PaisService paisService;
    @PostMapping("/insert")
    Pais insertPais(@RequestBody Pais pais){
        return  paisService.insertPais(pais);
    }

    @GetMapping("/all")
    Iterable<Pais> getAllPais(){
        return paisService.getPais();
    }
}
