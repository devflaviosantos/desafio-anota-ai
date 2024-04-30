package com.devflaviosantos.desafioanotaai.services;

import com.devflaviosantos.desafioanotaai.domain.category.Category;
import com.devflaviosantos.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.devflaviosantos.desafioanotaai.domain.product.Product;
import com.devflaviosantos.desafioanotaai.domain.product.ProductDTO;
import com.devflaviosantos.desafioanotaai.domain.product.exceptions.ProductNotFoundException;
import com.devflaviosantos.desafioanotaai.repositories.ProductRepository;
import com.devflaviosantos.desafioanotaai.services.aws.AwsSnsService;
import com.devflaviosantos.desafioanotaai.services.aws.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    private final AwsSnsService snsService;
    public ProductService(CategoryService categoryService, ProductRepository productRepository, AwsSnsService snsService){
        this.categoryService = categoryService;
        this.productRepository = productRepository;
        this.snsService = snsService;
    }
    public Product insert(ProductDTO productData){

        Category category = this.categoryService.getById(productData.categoryId()).orElseThrow(CategoryNotFoundException::new);
        Product product = new Product(productData);

        product = this.productRepository.save(product);
        this.snsService.publish(new MessageDTO(product.toString()));
        return product;
    }
    public Product update(String id, ProductDTO productDTO){
        Product product = this.productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        if (productDTO.categoryId() != null ){
            this.categoryService.getById(productDTO.categoryId())
                    .orElseThrow(CategoryNotFoundException::new);
            product.setCategoryId(productDTO.categoryId());
        }

        if (!productDTO.title().isEmpty()) product.setTitle(productDTO.title());
        if (!productDTO.description().isEmpty()) product.setDescription(productDTO.description());
        if (!(productDTO.price() == null)) product.setPrice(productDTO.price());

        this.productRepository.save(product);

        this.snsService.publish(new MessageDTO(product.toString()));

        return product;
    }
    public void delete(String id){

        Product product = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        this.productRepository.delete(product);
    }

    public List<Product> getAll(){
        return this.productRepository.findAll();
    }



}
