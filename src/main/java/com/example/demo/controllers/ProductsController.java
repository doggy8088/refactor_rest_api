package com.example.demo.controllers;

import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.models.Product;
import com.example.demo.services.ProductsService;
import com.example.demo.util.ProductResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Api(value = "Products")
@RestController
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @ApiOperation(value="gets all products")
    @GetMapping("/products")
    public ResponseEntity<ProductResponse> getAllProducts(){
        List<Product> productList = productsService.findAll();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setItems(productList);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @ApiOperation(value="creates a new product")
    @PostMapping("/products")
    public ResponseEntity<Product> create (@Valid @RequestBody Product product){
        Product savedProduct = productsService.save(product);
        // To show the URL in the location field
        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedProduct.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value="finds all products matching the specified name")
    @GetMapping(value="/products", params = "name")
    public ResponseEntity<ProductResponse> findProduct( @ApiParam(value="name") @RequestParam("name") String name) {
        List<Product> productList = productsService.find(name);
        if(productList.size()==0){
            throw new ProductNotFoundException("name - "+name);
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setItems(productList);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @ApiOperation(value="gets the project that matches the specified ID - ID is a GUID")
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable("id") String id){
        Optional<Product> product = productsService.findById(UUID.fromString(id));
        if(!product.isPresent()){
            throw new ProductNotFoundException("id - "+id);
        }
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }

    @ApiOperation(value="updates a product")
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @Valid @RequestBody Product product){
        Product updatedProduct = productsService.update(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @ApiOperation(value="deletes a product and its options")
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") String id){
        productsService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
