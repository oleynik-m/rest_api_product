package com.example.product.repo;

import com.example.product.model.Currency;
import com.example.product.model.Language;
import com.example.product.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends PagingAndSortingRepository<Product, Long> {

    /*
        используется postgre
     */

    Optional<Product> findByNameAndLanguageAndCurrency(String name,
                                                       Language language, Currency currency);
    List<Product> findAllByDescriptionAndLanguageAndCurrency(String description,
                                                             Language language, Currency currency, Pageable pageable);


}
