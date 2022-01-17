package com.coverflex.backend.api;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import com.coverflex.backend.api.model.ApiError;
import com.coverflex.backend.api.model.NewOrderInput;
import com.coverflex.backend.api.model.NewOrderOutput;
import com.coverflex.backend.api.model.OrderData;
import com.coverflex.backend.api.model.OrderInput;
import com.coverflex.backend.api.model.OrderOutput;
import com.coverflex.backend.exceptions.BaseException;
import com.coverflex.backend.exceptions.InsufficientBalanceException;
import com.coverflex.backend.exceptions.ProductAlreadyPurchasedException;
import com.coverflex.backend.exceptions.ProductNotFoundException;
import com.coverflex.backend.service.OrdersService;

import static org.mockito.Mockito.*;
class OrdersControllerTest {
    @Mock
    OrdersService ordersService;
    @Mock
    Logger log;
    @InjectMocks
    OrdersController ordersController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveOrder() throws BaseException {
        OrderOutput orderOutput = new OrderOutput("orderId", new OrderData(Arrays.<String>asList("String"), 0f));
        when(ordersService.saveOrder(any())).thenReturn(orderOutput);

        NewOrderOutput result = ordersController.saveOrder(new NewOrderInput(new OrderInput(Arrays.<String>asList("String"), "userid")));
        Assertions.assertEquals(orderOutput, result.getOrder());
    }

    @Test
    void testHandleEntityNotFound_generic() {
        ApiError result = ordersController.handleEntityNotFound(new BaseException());

        Assertions.assertEquals("generic", result.getError());
    }

    @Test
    void testHandleEntityNotFound_ProductNotFoundException() {
        ApiError result = ordersController.handleEntityNotFound(new ProductNotFoundException());

        Assertions.assertEquals("products_not_found", result.getError());
    }

    @Test
    void testHandleEntityNotFound_ProductAlreadyPurchasedException() {
        ApiError result = ordersController.handleEntityNotFound(new ProductAlreadyPurchasedException());

        Assertions.assertEquals("products_already_purchased", result.getError());
    }

    @Test
    void testHandleEntityNotFound_InsufficientBalanceException() {
        ApiError result = ordersController.handleEntityNotFound(new InsufficientBalanceException());

        Assertions.assertEquals("insufficient_balance", result.getError());
    }
}
