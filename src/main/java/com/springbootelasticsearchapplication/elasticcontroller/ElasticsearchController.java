package com.springbootelasticsearchapplication.elasticcontroller;

import com.springbootelasticsearchapplication.Pojo.ProductPojo;
import com.springbootelasticsearchapplication.repo.ElasticsearchQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class ElasticsearchController {

    @Autowired
    private ElasticsearchQuery elasticsearchQuery;

    @PostMapping("/createOrUpdateDocument")
    public ResponseEntity<Object> createOrUpdateDocument(@RequestBody ProductPojo productPojo) throws IOException {

        String response = elasticsearchQuery.createOrUpdateDocument(productPojo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getDocument")
    public ResponseEntity<Object> getDocumentById(@RequestParam String productId) throws IOException {
        ProductPojo productPojo = elasticsearchQuery.getDocumentById(productId);
        return new ResponseEntity<>(productPojo,HttpStatus.OK);
    }

    @DeleteMapping("/deleteDocument")
    public ResponseEntity<Object> deleteDocumentById(@RequestParam String productId) throws IOException {
        String response = elasticsearchQuery.deleteDocumentById(productId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/searchDocument")
    public ResponseEntity<Object> searchAllDocuments(@RequestParam String productId) throws IOException {
        List<ProductPojo> listProduct = elasticsearchQuery.searchAllDocuments();
        return new ResponseEntity<>(listProduct,HttpStatus.OK);
    }
}
