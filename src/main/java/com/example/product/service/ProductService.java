package com.example.product.service;

import com.example.product.model.Currency;
import com.example.product.model.Language;
import com.example.product.model.Product;
import com.example.product.repo.ProductRepo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService (ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Iterable<Product> getAllProducts(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    public List<Product> getProductsByDescription(String description, Language language, Currency currency, Pageable pageable) {
        return productRepo.findAllByDescriptionAndLanguageAndCurrency(description,language,currency,pageable);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepo.findById(id);
    }

    public Optional<Product> getProductByName(String name, Language language, Currency currency) {
        return productRepo.findByNameAndLanguageAndCurrency(name,language,currency);
    }



    public Product create(Product product) {
        return productRepo.save(product);
    }


}
