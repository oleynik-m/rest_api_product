package com.example.product.controller;

import com.example.product.model.Currency;
import com.example.product.model.Language;
import com.example.product.model.Product;
import com.example.product.repo.ProductRepo;
import com.example.product.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    public static final String DEFAULT_ERROR_VIEW = "error";

    private static final Logger logger = LogManager.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController (ProductService productService) {
        this.productService = productService;
    }

    // метод для получения всего списка продукции
    @GetMapping(params = { "page", "size" })
    public Iterable<Product> getAllProducts(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getAllProducts(pageable);
    }

    // метод для поиска продуктов по имени
    @GetMapping(value="/name",params = { "name", "language", "currency" })
    public Product getProductByName(@RequestParam("name") String name,
                                    @RequestParam("language") String language,
                                    @RequestParam("currency") String currency) {
        return productService.getProductByName(name,
                Language.valueOf(language),
                Currency.valueOf(currency)).
                get();
    }

    // метод для поиска продуктов по описанию
    @GetMapping(value="/description",params = { "description", "language", "currency","page","size" })
    public List<Product> getProductsByDescription(@RequestParam("description") String description,
                                                      @RequestParam("language") String language,
                                                      @RequestParam("currency") String currency,
                                                      @RequestParam("page") int page,
                                                      @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getProductsByDescription(description,
                Language.valueOf(language),
                Currency.valueOf(currency),
                pageable);
    }

    // метод получения по индентификатору
    @GetMapping("/{id}")
    public Product getProductById (@PathVariable Long id) {
            Optional<Product> product = productService.getProductById(id);
            return product.get();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED, reason = "OK")
    public void createProduct (@RequestBody Product product, HttpServletRequest request, HttpServletResponse response) {

        this.productService.create(product);

    }

    @GetMapping("/test")
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public String ok() {
        return "Class Level HTTP Status Overriden. The HTTP Status will be OK (CODE 200)\n";
    }

    @RequestMapping(
            value = "/process",
            method = RequestMethod.POST)
    public void process(@RequestBody Map<String, Object> payload)
            throws Exception {

        System.out.println(payload);

    }

    @ExceptionHandler({ NoSuchElementException.class })
  //  @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Product")
    @ResponseBody
    public Map<String,Object> handleException(HttpServletRequest req, NoSuchElementException ex) {
        logger.error("Request: " + req.getRequestURL() + " raised " + ex);
        Map<String,Object> response = new HashMap();
        response.put("errorCode",HttpStatus.NOT_FOUND.value());
        response.put("errorMessage",ex.getMessage());
        return response;
    }
}
