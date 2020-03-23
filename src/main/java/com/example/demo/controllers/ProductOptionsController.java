package com.example.demo.controllers;

import com.example.demo.models.ProductOptions;
import com.example.demo.services.ProductOptionsService;
import com.example.demo.util.ProductOptionsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api(value = "ProductOptions")
@RestController
public class ProductOptionsController {

    @Autowired
    private ProductOptionsService productOptionsService;

    @ApiOperation(value="finds all options for a specified product")
    @GetMapping("/products/{id}/options")
    public ResponseEntity<ProductOptionsResponse> getAllOptions(@PathVariable("id") String id) {
        List<ProductOptions> productOptionsList = productOptionsService.getAllOptions(id);
        ProductOptionsResponse productOptionsResponse = new ProductOptionsResponse();
        productOptionsResponse.setItems(productOptionsList);
        return new ResponseEntity<>(productOptionsResponse, HttpStatus.OK);
    }

    @ApiOperation(value="adds a new product option to the specified product")
    @PostMapping("/products/{id}/options")
    public ResponseEntity<ProductOptions> create(@PathVariable("id") String id, @Valid @RequestBody ProductOptions productOptions){
        ProductOptions createdOptions = productOptionsService.save(id, productOptions);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdOptions.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value="finds the specified product option for the specified product")
    @GetMapping("/products/{id}/options/{optionId}")
    public ResponseEntity<ProductOptions> findProductOptionById(@PathVariable("id") String id, @PathVariable("optionId") String optionId){
        return new ResponseEntity<>(productOptionsService.getProductOption(id, optionId), HttpStatus.OK);
    }

    @ApiOperation(value="updates the specified product option")
    @PutMapping("/products/{id}/options/{optionId}")
    public ResponseEntity<ProductOptions> updateOption(@PathVariable("id") String id, @PathVariable("optionId") String optionId, @RequestBody ProductOptions productOptions) {
        ProductOptions updatedProduct = productOptionsService.updateOption(id, optionId, productOptions);
        return ResponseEntity.ok(updatedProduct);
    }

    @ApiOperation(value="deletes the specified product option")
    @DeleteMapping("/products/{id}/options/{optionId}")
    public ResponseEntity<ProductOptions> deleteOption(@PathVariable("id") String productId, @PathVariable("optionId") String optionId) {
        productOptionsService.deleteOption(productId, optionId);
        return ResponseEntity.ok().build();
    }

}
