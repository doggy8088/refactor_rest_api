package com.example.demo;

import com.example.demo.models.Product;
import com.example.demo.repository.ProductsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;
import java.util.UUID;

/**
 *  Main class to start the application.
 */
@SpringBootApplication
public class ProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsApplication.class, args);
	}

	/*
	@Bean
	public CommandLineRunner setup(ProductsRepository productsRepository){
		return  (args) ->{
			productsRepository.save(new Product("Samsung Galaxy S7", "Newest mobile product from Samsung.", 1024.99, 16.99));
			productsRepository.save(new Product( "Apple iPhone 6S", "Newest mobile product from Apple.", 1299.99, 15.99));
		};
	}*/

	@Bean
	public LocaleResolver localeResolver() {
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}

}
