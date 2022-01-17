package com.coverflex.backend.service.impl;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.coverflex.backend.api.model.Product;
import com.coverflex.backend.repository.ProductRepository;
import com.coverflex.backend.service.mapper.ProductMapper;

import static org.mockito.Mockito.*;
class ProductServiceImplTest {
    @Mock
    ProductRepository productRepository;
    @Mock
    ProductMapper productMapper;
    @InjectMocks
    ProductServiceImpl productServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetProducts() {
        when(productMapper.fromRepositoryToModelProductList(any())).thenReturn(Arrays.<Product>asList(new Product("id", "name", 0f)));

        List<Product> result = productServiceImpl.getProducts();
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("id", result.get(0).getId());
    }
}
