package com.example.product;

import com.example.product.controller.ProductController;
import com.example.product.model.Currency;
import com.example.product.model.Language;
import com.example.product.model.Product;
import com.example.product.repo.ProductRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ProductApplication {

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final Logger logger = LogManager.getLogger(ProductApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ProductRepo productRepo) {
		return args -> {
			try {
				List<Product> productList = Arrays.asList(
						new Product("Помидоры", "Овощи", new BigDecimal(100.0), Currency.RUB,
								Language.RU),
						new Product("Огурцы", "Овощи", new BigDecimal(200.0),
								Currency.RUB, Language.RU),
						new Product("Морковь", "Овощи", new BigDecimal(300.0),
								Currency.RUB, Language.RU),
						new Product("Tomatoes", "Vegetables", new BigDecimal(10.0),
								Currency.USD, Language.EN),
						new Product("Cucumbers", "Vegetables", new BigDecimal(20.0),
								Currency.USD, Language.EN),
						new Product("Carrot", "Vegetables", new BigDecimal(30.0),
								Currency.USD, Language.EN)
				);
				for (Product p :productList ) {
					p.setCreateDate(dateFormat.format(new Date ()));
					p.setUpdateDate(dateFormat.format(new Date ()));
				}
				productRepo.saveAll(productList);
			}
			catch (Exception e) {
				logger.error(e);
			}
		};
	}

}
