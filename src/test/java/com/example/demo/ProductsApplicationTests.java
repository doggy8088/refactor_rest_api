package com.example.demo;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductsApplicationTests {

	@InjectMocks
	ProductsApplication productsApplication;

	@Test
	void contextLoads() {
	}

	@Test
	public void main() {
		String[] arg = {"start"};
		productsApplication.main(arg);
	}

}
