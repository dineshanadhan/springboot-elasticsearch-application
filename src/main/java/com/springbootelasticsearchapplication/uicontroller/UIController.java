package com.springbootelasticsearchapplication.uicontroller;

import com.springbootelasticsearchapplication.Pojo.ProductPojo;
import com.springbootelasticsearchapplication.repo.ElasticsearchQuery;
import org.springframework.beans.AbstractNestablePropertyAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class UIController {

    @Autowired
    private ElasticsearchQuery elasticsearchQuery;

    @GetMapping("/")
    public String viewHomePage(Model model) throws IOException {
        model.addAttribute("ListProductDocuments",elasticsearchQuery.searchAllDocuments());
        return "index";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product")ProductPojo productPojo) throws IOException {
        elasticsearchQuery.createOrUpdateDocument(productPojo);
        return "redirect:/";
    }

    @GetMapping("/showFormUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") String id, Model model) throws IOException {
        ProductPojo productPojo = elasticsearchQuery.getDocumentById(id);
        model.addAttribute("product",productPojo);
        return "updateProductDocument";
    }

    @GetMapping("showNewProductForm")
    public String showNewProductForm(Model model) {
        ProductPojo productPojo = new ProductPojo();
        model.addAttribute("product",productPojo);
        return "newProductDocument";
    }

    @DeleteMapping("deleteProduct/{id}")
    public String deleteProduct(@PathVariable(value = "id") String id) throws IOException{
        this.elasticsearchQuery.deleteDocumentById(id);
        return "redirect:/";
    }
}
