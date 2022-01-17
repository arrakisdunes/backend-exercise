package com.coverflex.backend.api;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import com.coverflex.backend.api.model.GetProductsOutput;
import com.coverflex.backend.api.model.Product;
import com.coverflex.backend.service.ProductsService;

import static org.mockito.Mockito.*;
class ProductsControllerTest {
    @Mock
    ProductsService productsService;
    @Mock
    Logger log;
    @InjectMocks
    ProductsController productsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = Arrays.<Product>asList(new Product("id", "name", 0f));
        when(productsService.getProducts()).thenReturn(products);

        GetProductsOutput result = productsController.getAllProducts();
        Assertions.assertEquals(products, result.getProducts());
    }
}