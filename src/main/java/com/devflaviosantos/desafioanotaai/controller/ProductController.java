package com.devflaviosantos.desafioanotaai.controller;


import com.devflaviosantos.desafioanotaai.domain.product.Product;
import com.devflaviosantos.desafioanotaai.domain.product.ProductDTO;
import com.devflaviosantos.desafioanotaai.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody ProductDTO productDTO){
        Product product = this.service.insert(productDTO);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll(){
        List<Product> products = this.service.getAll();
        return ResponseEntity.ok().body(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable("id") String id, @RequestBody ProductDTO productDTO){
        Product updateProduct = this.service.update(id, productDTO);
        return ResponseEntity.ok().body(updateProduct);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable("id") String id){
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
