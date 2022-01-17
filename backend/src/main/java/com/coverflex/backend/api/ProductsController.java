package com.coverflex.backend.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coverflex.backend.api.model.GetProductsOutput;
import com.coverflex.backend.service.ProductsService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = {"/api/products"}, produces = {APPLICATION_JSON_VALUE})
@Slf4j
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping
    public GetProductsOutput getAllProducts() {
        log.info("Start Get /api/products");
        return new GetProductsOutput(productsService.getProducts());
    }
}
