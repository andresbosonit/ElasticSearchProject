package com.example.backendelasticsearch.util;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.PrefixQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.val;

import java.util.function.Supplier;

public class ESUtil {


//    public static Supplier<Query> createSupplierAutoSuggest(String partialProductName) {
//
//        return () -> Query.of(q -> q.match(createAutoSuggestMatchQuery(partialProductName)));
//    }

    public static MatchQuery createAutoSuggestMatchQuery1(String partialProductName) {
        val autoSuggestQuery = new MatchQuery.Builder();
        return autoSuggestQuery.field("name").query(partialProductName).analyzer("standard").build();

    }

    public static Query createMatchQuery(String field, String nombre){
        return Query.of(q -> q.match(m -> m
                .field("name")
                .query(nombre)
        ));
    }

    public static Query createQueryMatchPaisAndPrefixProduct(String pais, String partialName) {
        return Query.of(q -> q.bool(b -> b
                .must(createAutoSuggestMatchQuery(partialName))
            )
        );
   }

    private static Query createAutoSuggestMatchQuery(String partialName) {
        return Query.of(q -> q
                .prefix(p -> p
                        .field("name")
                        .value(partialName))
        );
    }

    private static BoolQuery boolQuery() {
        return new BoolQuery.Builder().build();
    }

    public static String paisTraducido(String pais) {
        switch (pais) {
            case "Spain":
                return "España";
            case "Germany":
                return "Alemania";
            case "United Kingdom":
                return "Inglaterra";
            default:
                return pais;
        }
    }
    public static Supplier<Query> createSupplierPrefix(String namePrefix) {
        Supplier<Query> supplier = () -> Query.of(q ->
                q.prefix(createPrefixQuery(namePrefix.toLowerCase())));
        return supplier;
    }
    public static PrefixQuery createPrefixQuery(String namePrefix) {
        return new PrefixQuery.Builder()
                .field("name")
                .value(namePrefix)
                .build();
    }
}
