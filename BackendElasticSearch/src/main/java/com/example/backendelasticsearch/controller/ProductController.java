package com.example.backendelasticsearch.controller;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.example.backendelasticsearch.entity.Pais;
import com.example.backendelasticsearch.entity.Product;
import com.example.backendelasticsearch.repo.PaisRepo;
import com.example.backendelasticsearch.service.PaisService;
import com.example.backendelasticsearch.service.ProductService;
import com.example.backendelasticsearch.service.impl.ESService;
import jakarta.annotation.PostConstruct;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.backendelasticsearch.util.ESUtil.paisTraducido;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private PaisService paisService;

    @Autowired
    private ESService esService;

    @Autowired
    private PaisRepo paisRepo;

    private ElasticsearchClient client;
    public ProductController(){
        RestClient restClient = RestClient
                .builder(HttpHost.create("http://localhost:9200"))
                .build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        client = new ElasticsearchClient(transport);
    }

    @PostConstruct
    public void addProducts(){
        Pais spain = new Pais("España", new ArrayList<>());
        Pais unitedKindom = new Pais("Inglaterra", new ArrayList<>());
        Pais germany = new Pais("Alemania", new ArrayList<>());

        paisService.insertPais(spain);
        paisService.insertPais(unitedKindom);
        paisService.insertPais(germany);

        Product nintendo = new Product(1,"Nintendo",2,130,null);
        Product psp = new Product(2,"PSP",2,130,null);
        Product ps5 = new Product(3,"PS5",2,130,null);

        productService.insertProduct(nintendo, new String[]{"España", "Inglaterra", "Alemania"});
        productService.insertProduct(psp, new String[]{"España", "Inglaterra"});
        productService.insertProduct(ps5, new String[]{"Inglaterra", "Alemania"});
    }
    @GetMapping("/all")
    Iterable<Product> getAllProducts(){
        return productService.getProduct();
    }

    @PostMapping("/insert")
    Product insertProduct(@RequestBody Product product, @RequestParam String[] paises){
        return  productService.insertProduct(product,paises);
    }

    @GetMapping("/autoSuggest/{pais}/{partialProductName}")
    List<String> autoSuggestProductSearch(@PathVariable String pais, @PathVariable String partialProductName) throws IOException {

        // Saco los productos del pais en el que se esta buscando
        SearchResponse<Pais> searchResponsePais = client.search(s -> s
                .index("pais")
                .query(q -> q
                        .match(t -> t
                                .field("name")
                                .query(paisTraducido(pais)))), Pais.class);

        List<Hit<Pais>> hits = searchResponsePais.hits().hits();
        List<Product> productListPais = new ArrayList<>();
        for(Hit<Pais> hit : hits){
            productListPais= hit.source().getProductos();
        }
        List<String> listOfProductNamesPais = new ArrayList<>();
        for(Product product : productListPais){
            listOfProductNamesPais.add(product.getName())  ;
        }

        // Voy buscando los productos cuando se va escribiendo en el input-text
        SearchResponse<Product> searchResponse = esService.autoSuggestProduct(partialProductName);
        List<Hit<Product>> hitList  =  searchResponse.hits().hits();
        List<Product> productList = new ArrayList<>();
        for(Hit<Product> hit : hitList){
            productList.add(hit.source());
        }
        List<String> listOfProductNames = new ArrayList<>();
        for(Product product : productList){
            listOfProductNames.add(product.getName())  ;
        }

        //Hago la intersección de las dos listas
        Set<String> set1 = new HashSet<>(listOfProductNamesPais);
        Set<String> set2 = new HashSet<>(listOfProductNames);
        set1.retainAll(set2);
        List<String> elementosComunes = new ArrayList<>(set1);
        return elementosComunes;
    }


    // Ejemplo explicado en el documento
    /*@GetMapping("/exampleElastic/{partialProductName}")
    List<String> autoSuggestProductSearch(@PathVariable String partialProductName) throws IOException {
        Supplier< Query > supplier = ESUtil.createSupplierPrefix(partialProductName);
        SearchResponse<Product> searchResponse = client
                .search(s -> s.index("products").query(supplier.get()), Product.class);
        System.out.println(" elasticsearch auto suggestion query" + supplier.get().toString());
        List<Hit<Product>> hitList  =  searchResponse.hits().hits();
        List<Product> productList = new ArrayList<>();
        for(Hit<Product> hit : hitList){
            productList.add(hit.source());
        }
        List<String> listOfProductNames = new ArrayList<>();
        for(Product product : productList){
            listOfProductNames.add(product.getName())  ;
        }
        return listOfProductNames;
    }*/

}