package com.example.demo;

import com.example.demo.controllers.ProductOptionsController;
import com.example.demo.controllers.ProductsController;
import com.example.demo.models.Product;
import com.example.demo.models.ProductOptions;
import com.example.demo.services.ProductOptionsService;
import com.example.demo.services.ProductsService;
import com.example.demo.util.ProductOptionsResponse;
import com.example.demo.util.ProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *  ExtendWith is introduced in Spring 5 to integrate with JUNIT Jupiter test
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest({ProductsController.class, ProductOptionsController.class})
public class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductsService productsService;

    @MockBean
    private ProductOptionsService productOptionsService;

    @MockBean
    private ProductsController productsController;

    @MockBean
    private ProductOptionsController productOptionsController;

    @Test
    void getAllProducts() throws Exception {
        List<Product> productsList = new ArrayList<>();
        productsList.add(new Product(UUID.randomUUID(), "Samsung Galaxy 10", "Newest mobile product from Samsung.", 1024.99, 16.99));
        productsList.add(new Product(UUID.randomUUID(), "Apple iPhone 6S", "Newest mobile product from Apple.", 1299.99, 15.99));
        ProductResponse productResponse = new ProductResponse();
        productResponse.setItems(productsList);
        when(productsController.getAllProducts()).thenReturn(new ResponseEntity<>(productResponse, HttpStatus.OK));
        //when(productsController.getAllProducts()).thenReturn(new HttpproductResponse);

        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.items", hasSize(2)))
                .andExpect(jsonPath("$.items[0].name").value("Samsung Galaxy 10"))
                .andExpect(jsonPath("$.items[1].name").value("Apple iPhone 6S"));

    }

    @Test
    void saveProduct() throws Exception {
        String id = "f93975b1-7fc4-4652-8808-236b8f071601";
        UUID uuid = UUID.fromString(id);
        Product product = new Product( uuid, "Samsung Galaxy S7", "Newest mobile product from Samsung.", 1024.99, 16.99);
        when(productsController.create(any(Product.class))).thenReturn(new ResponseEntity<>(product, HttpStatus.CREATED));

        // Java Object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String productJson = objectMapper.writeValueAsString(product);

        ResultActions result = mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson));

        result.andExpect(status().isCreated());
    }

    @Test
    void findProductByName() throws Exception {
        String name = "Samsung Galaxy S7";
        List<Product> productsList = new ArrayList<>();
        productsList.add(new Product( "Samsung Galaxy S7", "Newest mobile product from Samsung.", 1024.99, 16.99));
        productsList.add(new Product( "Samsung Galaxy S7", "Newest mobile product from Samsung.", 1024.99, 16.99));
        ProductResponse productResponse = new ProductResponse();
        productResponse.setItems(productsList);
        when(productsController.findProduct(name)).thenReturn( new ResponseEntity<>(productResponse, HttpStatus.OK));

        ResultActions result = mockMvc.perform(get("/products")
                .param("name", name)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(jsonPath("$.items[0].name").value(name));
        result.andExpect(jsonPath("$.items[1].name").value(name));
    }

    @Test
    void findProductById() throws Exception {
        String id = "f93975b1-7fc4-4652-8808-236b8f071601";
        UUID uuid = UUID.fromString(id);
        Product product = new Product( uuid, "Samsung Galaxy S7", "Newest mobile product from Samsung.", 1024.99, 16.99);
        when(productsController.findProductById(id)).thenReturn(new ResponseEntity<>(product, HttpStatus.OK));

        ResultActions resultActions = mockMvc.perform(get("/products/{id}",uuid)
                                        .contentType(MediaType.APPLICATION_JSON));
                        resultActions.andExpect(jsonPath("$.id").value(uuid.toString()));
                        resultActions.andExpect(jsonPath("$.name").value("Samsung Galaxy S7"));

    }

    @Test
    void updateProductById() throws Exception {
        Product product = new Product( "Samsung Galaxy S7", "Newest mobile product from Samsung.", 1024.99, 16.99);
        /*doNothing()
                .doThrow(new RuntimeException())
                .when(productsController).update(any(String.class), any(Product.class));*/
        when(productsController.updateProduct(any(String.class),any(Product.class))).thenReturn(ResponseEntity.ok(product));

        ObjectMapper objectMapper = new ObjectMapper();
        String productJson = objectMapper.writeValueAsString(product);
        String id = "ada60d9c-00c5-41ef-a180-0224f525b931";
        ResultActions resultActions = mockMvc.perform(put("/products/{id}", id)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(productJson));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.name").value("Samsung Galaxy S7"));
    }

    @Test
    void deleteProductById() throws Exception {
        String id = "f93975b1-7fc4-4652-8808-236b8f071601";
        when(productsController.deleteProduct(any(String.class))).thenReturn(ResponseEntity.ok().build());
        ResultActions resultActions = mockMvc.perform(delete("/products/{id}", id));
        resultActions.andExpect(status().isOk());
    }

    // Product Options related Tests

    @Test
    void getAllOptions() throws Exception {
        List<ProductOptions> productOptionsList = new ArrayList<>();
        Product product = new Product("Samsung Galaxy 10", "Newest mobile product from Samsung.", 1024.99, 16.99);
        productOptionsList.add(new ProductOptions(product, "White","White Samsung Galaxy S7"));
        productOptionsList.add(new ProductOptions(product, "Black","Black Samsung Galaxy S7"));
        ProductOptionsResponse productOptionsResponse = new ProductOptionsResponse();
        productOptionsResponse.setItems(productOptionsList);
        String id = "f93975b1-7fc4-4652-8808-236b8f071601";
        when(productOptionsController.getAllOptions(id)).thenReturn(new ResponseEntity<>(productOptionsResponse, HttpStatus.OK));

        mockMvc.perform(get("/products/{id}/options",id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items", hasSize(2))).andDo(print());

    }

    @Test
    void saveProductOptions() throws Exception {
        String id = "f93975b1-7fc4-4652-8808-236b8f071601";
        UUID uuid = UUID.fromString(id);
        Product product = new Product( uuid,"Samsung Galaxy S7", "Newest mobile product from Samsung.", 1024.99, 16.99);
        ProductOptions productOptions = new ProductOptions(uuid, product, "White","White Samsung Galaxy S7");
        when(productOptionsController.create(any(String.class), any(ProductOptions.class))).thenReturn(new ResponseEntity<>(productOptions, HttpStatus.CREATED));

        ObjectMapper objectMapper = new ObjectMapper();
        String productOptionsJson = objectMapper.writeValueAsString(productOptions);
        mockMvc.perform(post("/products/{id}/options", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productOptionsJson))
                .andExpect(status().isCreated());
    }

    @Test
    void getOption() throws Exception {
        Product product = new Product( "Samsung Galaxy S7", "Newest mobile product from Samsung.", 1024.99, 16.99);
        ProductOptions productOptions = new ProductOptions(product, "White","White Samsung Galaxy S7");
        String id = "ada60d9c-00c5-41ef-a180-0224f525b931";
        String optionId = "123";
        when(productOptionsController.findProductOptionById(id, optionId)).thenReturn(new ResponseEntity<>(productOptions, HttpStatus.OK));

        ResultActions resultActions = mockMvc.perform(get("/products/{productId}/options/{optionId}",id, optionId)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(jsonPath("$.name").value("White"));
        resultActions.andExpect(jsonPath("$.description").value("White Samsung Galaxy S7"));
    }

    @Test
    void updateOption() throws Exception {
        Product product = new Product( "Samsung Galaxy S7", "Newest mobile product from Samsung.", 1024.99, 16.99);
        ProductOptions productOptions = new ProductOptions(product, "White","White Samsung Galaxy S7");
        String id = "ada60d9c-00c5-41ef-a180-0224f525b931";
        String optionId = "ada60d9c-00c5-41ef-a180-0224f525b931";
        when(productOptionsController.updateOption(id, optionId, productOptions)).thenReturn(ResponseEntity.ok(productOptions));
        ObjectMapper objectMapper = new ObjectMapper();
        String productOptionsJson = objectMapper.writeValueAsString(product);
        ResultActions resultActions = mockMvc.perform(put("/products/{productId}/options/{optionId}",id, optionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(productOptionsJson));
        resultActions.andExpect(status().isOk());
    }

    @Test
    void deleteOption() throws Exception {
        String productId = "ada60d9c-00c5-41ef-a180-0224f525b931";
        String optionId = "ada60d9c-00c5-41ef-a180-0224f525b931";
        Product product = new Product( "Samsung Galaxy S7", "Newest mobile product from Samsung.", 1024.99, 16.99);
        ProductOptions productOptions = new ProductOptions(product, "White","White Samsung Galaxy S7");
        when(productOptionsController.deleteOption(productId, optionId)).thenReturn(ResponseEntity.ok(productOptions));

        ResultActions resultActions = mockMvc.perform(delete("/products/{productId}/options/{optionId}",productId, optionId)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }

}
