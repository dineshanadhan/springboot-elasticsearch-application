package com.springbootelasticsearchapplication.repo;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.springbootelasticsearchapplication.Pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import java.io.IOError;
import java.io.IOException;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ElasticsearchQuery {

    private ElasticsearchClient elasticsearchClient;
    private final String indexName = "products";

    public String createOrUpdateDocument(ProductPojo productPojo) throws IOException {

        IndexResponse indexResponse = elasticsearchClient.index(i -> i
                .index(indexName)
                .id(productPojo.getId())
                .document(productPojo));

        if (indexResponse.result().name().equals("Created")) {
            return new StringBuilder("Document has been successfully created...").toString();
        } else if (indexResponse.result().name().equals("Updated")) {
            return new StringBuilder("Document has been successfully updated...").toString();
        } else {
            return new StringBuilder("Error while performing the operation...").toString();
        }

    }

    public ProductPojo getDocumentById (String productId) throws IOException {

        ProductPojo productPojo = null;
        GetResponse<ProductPojo> response = elasticsearchClient.get(g -> g
                        .index(indexName)
                        .id(productId),
                        ProductPojo.class);

        if (response.found()) {
            productPojo = response.source();
            System.out.println("Product name " + productPojo.getName());
        } else {
            System.out.println ("Product not found");
        }
        return productPojo;
    }

    public String deleteDocumentById(String productId) throws IOException {

        DeleteRequest deleteRequest = DeleteRequest.of(d ->
                d.index(indexName).id(productId));

        DeleteResponse deleteResponse = elasticsearchClient.delete(deleteRequest);

        if (Objects.nonNull(deleteResponse.result()) && !deleteResponse.result().name().equals("NotFound")) {
            return new StringBuilder("Product with id " + deleteResponse.id() + " has been deleted.").toString();
        } else {
            System.out.println("Product not found");
            return new StringBuilder("Product with id " + deleteResponse.id()+" does not exist.").toString();
        }
    }

    public List<ProductPojo> searchAllDocuments() throws IOException {

        SearchRequest searchRequest = SearchRequest.of(s -> s.index(indexName));
        SearchResponse searchResponse = elasticsearchClient.search(searchRequest,ProductPojo.class);
        List<Hit> hits = searchResponse.hits().hits();
        List<ProductPojo> productList = new ArrayList<>();

        for (Hit object : hits) {
            System.out.print(((ProductPojo) object.source()));
            productList.add((ProductPojo) object.source());
        }
        return productList;
    }

}
