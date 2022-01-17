package com.coverflex.backend.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.coverflex.backend.api.model.ApiError;
import com.coverflex.backend.api.model.NewOrderInput;
import com.coverflex.backend.api.model.NewOrderOutput;
import com.coverflex.backend.api.model.OrderOutput;
import com.coverflex.backend.exceptions.BaseException;
import com.coverflex.backend.exceptions.InsufficientBalanceException;
import com.coverflex.backend.exceptions.ProductAlreadyPurchasedException;
import com.coverflex.backend.exceptions.ProductNotFoundException;
import com.coverflex.backend.service.OrdersService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = {"/api/orders"}, produces = {APPLICATION_JSON_VALUE})
@Slf4j
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping
    public NewOrderOutput saveOrder(@RequestBody NewOrderInput orderIn) throws BaseException {
        log.info("Start Post /api/orders body:", orderIn);
        return new NewOrderOutput(ordersService.saveOrder(orderIn.getOrder()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseException.class)
    protected ApiError handleEntityNotFound(BaseException ex) {
        if(ex instanceof ProductNotFoundException){
            return new ApiError("products_not_found");
        }
        if(ex instanceof ProductAlreadyPurchasedException){
            return new ApiError("products_already_purchased");
        }
        if(ex instanceof InsufficientBalanceException){
            return new ApiError("insufficient_balance");
        }
        return new ApiError("generic");
    }
}
