package com.coverflex.backend.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import com.coverflex.backend.api.model.OrderData;
import com.coverflex.backend.api.model.OrderInput;
import com.coverflex.backend.api.model.OrderOutput;
import com.coverflex.backend.exceptions.BaseException;
import com.coverflex.backend.exceptions.InsufficientBalanceException;
import com.coverflex.backend.exceptions.ProductAlreadyPurchasedException;
import com.coverflex.backend.exceptions.ProductNotFoundException;
import com.coverflex.backend.repository.OrdersRepository;
import com.coverflex.backend.repository.ProductRepository;
import com.coverflex.backend.repository.UserRepository;
import com.coverflex.backend.repository.entity.Orders;
import com.coverflex.backend.repository.entity.Product;
import com.coverflex.backend.repository.entity.User;
import com.coverflex.backend.service.UserService;

import static org.mockito.Mockito.*;
class OrdersServiceImplTest {
    public static final Product PRODUCT = new Product("String", "name", 10f);
    public static final User USER = new User(Long.valueOf(1), "userId", 10f);
    @Mock
    OrdersRepository ordersRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    UserService userService;
    @Mock
    ProductRepository productRepository;
    @Mock
    Logger log;
    @InjectMocks
    OrdersServiceImpl ordersServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(userRepository.save(any(User.class))).thenReturn(null);

        Set<Product> products = new HashSet<>();
        products.add(PRODUCT);
        Orders orderDb = new Orders(Long.valueOf(1), 0f, products, USER);
        when(ordersRepository.save(any(Orders.class))).thenReturn(orderDb);
    }

    @Test
    void testSaveOrder() throws BaseException {
        when(ordersRepository.findByUser_UserId(anyString())).thenReturn(null);
        when(userService.getOrCreate(anyString())).thenReturn(USER);
        when(productRepository.findByIds(any())).thenReturn(Arrays.<Product>asList(new Product("String", "name", 0f)));

        OrderOutput result = ordersServiceImpl.saveOrder(new OrderInput(Arrays.<String>asList("String"), "userid"));
        Assertions.assertEquals("1", result.getOrderId());
        Assertions.assertEquals(1, result.getData().getItems().size());
    }

    @Test
    void testSaveOrder_ProductNotFoundException() throws BaseException {
        when(ordersRepository.findByUser_UserId(anyString())).thenReturn(null);
        when(userService.getOrCreate(anyString())).thenReturn(USER);
        when(productRepository.findByIds(any())).thenReturn(Arrays.<Product>asList());

        Assertions.assertThrows(ProductNotFoundException.class, () -> {
            ordersServiceImpl.saveOrder(new OrderInput(Arrays.<String>asList("String"), "userid"));
        });
    }

    @Test
    void testSaveOrder_InsufficientBalanceException() throws BaseException {
        when(ordersRepository.findByUser_UserId(anyString())).thenReturn(null);
        when(userService.getOrCreate(anyString())).thenReturn(new User(Long.valueOf(1), "userId", 0f));
        when(productRepository.findByIds(any())).thenReturn(Arrays.<Product>asList(PRODUCT));

        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            ordersServiceImpl.saveOrder(new OrderInput(Arrays.<String>asList("String"), "userid"));
        });
    }

    @Test
    void testSaveOrder_ProductAlreadyPurchasedException() throws BaseException {
        Set<Product> products = new HashSet<>();
        products.add(PRODUCT);

        when(ordersRepository.findByUser_UserId(anyString())).thenReturn(Arrays.asList(new Orders(Long.valueOf(1), 10f, products, new User(Long.valueOf(1), "userId", 20f))));
        when(userService.getOrCreate(anyString())).thenReturn(new User(Long.valueOf(1), "userId", 20f));
        when(productRepository.findByIds(any())).thenReturn(Arrays.<Product>asList(PRODUCT));

        Assertions.assertThrows(ProductAlreadyPurchasedException.class, () -> {
            ordersServiceImpl.saveOrder(new OrderInput(Arrays.<String>asList("String"), "userid"));
        });
    }


}
