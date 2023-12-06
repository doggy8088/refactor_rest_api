package com.example.demo;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controllers.ProductsController;
import com.example.demo.models.Product;
import com.example.demo.util.ProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ExtendWith is introduced in Spring 5 to integrate with JUNIT Jupiter test
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest({ProductsController.class, ObjectMapper.class})
public class ProductsControllerTest extends AbstractProductTest{

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductsController productsController;

    private static final String PATH_PREFIX = "/products";
    private static final String PATH_PREFIX_WITH_ID = "/products/{id}";


    @Test
    void getAllProducts() throws Exception {
        // mock
        ProductResponse productResponse = new ProductResponse(products);
        when(productsController.getAllProducts())
                .thenReturn(new ResponseEntity<>(productResponse, OK));

        mockMvc.perform(get(PATH_PREFIX)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(2)))
                .andExpect(jsonPath("$.items[0].name").value(samsungGalaxy.getName()))
                .andExpect(jsonPath("$.items[1].name").value(iPhone6S.getName()));

    }

    @Test
    void saveProduct() throws Exception {
        // mock
        when(productsController.create(any(Product.class)))
                .thenReturn(new ResponseEntity<>(samsungGalaxy, CREATED));

        String productJson = objectMapper.writeValueAsString(samsungGalaxy);

        mockMvc.perform(post(PATH_PREFIX)
                        .contentType(APPLICATION_JSON)
                        .content(productJson))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isCreated());
    }

    @Test
    void findProductByName() throws Exception {
        // mock
        String samsungGalaxyName = samsungGalaxy.getName();
        when(productsController.findProduct(samsungGalaxyName))
                .thenReturn(new ResponseEntity<>(new ProductResponse(products), OK));

        mockMvc.perform(get(PATH_PREFIX)
                        .param("name", samsungGalaxyName)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].name").value(samsungGalaxyName));
    }

    @Test
    void findProductById() throws Exception {
        String id = samsungGalaxy.getId();
        when(productsController.findProductById(id))
                .thenReturn(new ResponseEntity<>(samsungGalaxy, OK));

        mockMvc.perform(get(PATH_PREFIX_WITH_ID, id)
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(samsungGalaxy.getName()));

    }

    @Test
    void updateProductById() throws Exception {
        when(productsController.updateProduct(any(Product.class)))
                .thenReturn(ResponseEntity.ok(samsungGalaxy));

        String productJson = objectMapper.writeValueAsString(samsungGalaxy);

        mockMvc.perform(put(PATH_PREFIX)
                        .contentType(APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(samsungGalaxy.getName()));
    }

    @Test
    void deleteProductById() throws Exception {
        when(productsController.deleteProduct(any(String.class)))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete(PATH_PREFIX_WITH_ID, samsungGalaxy.getId()))
                .andExpect(status().isOk());
    }
}
