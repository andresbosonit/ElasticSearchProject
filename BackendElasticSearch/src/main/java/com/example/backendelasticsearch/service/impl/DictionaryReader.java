package com.example.backendelasticsearch.service.impl;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DictionaryReader {
    public List<String> readDictionary(String filePath) throws IOException {
        List<String> dictionary = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line);
            }
        }
        return dictionary;
    }
}

