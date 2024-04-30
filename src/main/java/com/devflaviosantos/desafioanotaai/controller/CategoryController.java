package com.devflaviosantos.desafioanotaai.controller;

import com.devflaviosantos.desafioanotaai.domain.category.Category;
import com.devflaviosantos.desafioanotaai.domain.category.CategoryDTO;
import com.devflaviosantos.desafioanotaai.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Category> insert(@RequestBody CategoryDTO categoryData){
        Category category = this.service.insert(categoryData);
        return ResponseEntity.ok().body(category);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll(){
        List<Category> categories = this.service.getAll();
        return ResponseEntity.ok().body(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") String id, @RequestBody CategoryDTO categoryDTO){
        Category updateCategory = this.service.update(id, categoryDTO);
        return ResponseEntity.ok().body(updateCategory);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") String id){
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
