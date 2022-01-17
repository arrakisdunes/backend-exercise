package com.coverflex.backend.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import com.coverflex.backend.service.OrdersService;
import com.coverflex.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    @Override
    public OrderOutput saveOrder(OrderInput order) throws BaseException {
        try {
            User user = userService.getOrCreate(order.getUserid());
            List<Product> products = productRepository.findByIds(order.getItems());

            checkProducts(order, products);

            AtomicReference<Float> total = new AtomicReference<>((float) 0);
            products.forEach(p -> total.updateAndGet(v -> (float) (v + p.getPrice())));

            float newBalance = user.getBalance() - total.get().floatValue();
            checkBalance(newBalance);

            user.setBalance(newBalance);
            userRepository.save(user);
            Orders orderdb = addOrder(user, products, total);

            return map(order, total, orderdb);
        } catch (BaseException e){
            log.error("BaseException",e);
            throw e;
        }
    }

    private boolean hasElementProd(List<Product> p1, Set<Product> p2){
        for (Product p: p1) {
            if(p2.contains(p)) {
                return true;
            }
        }
        return false;
    }

    private OrderOutput map(OrderInput order, AtomicReference<Float> total, Orders orderdb) {
        OrderOutput output = new OrderOutput();
        output.setOrderId(orderdb.getOrderId().toString());
        OrderData data = new OrderData();
        data.setItems(order.getItems());
        data.setTotal(total.get());
        output.setData(data);

        return output;
    }

    private Orders addOrder(User user, List<Product> products, AtomicReference<Float> total) {
        Orders orderdb = new Orders();
        orderdb.setTotalOrder(total.get());
        orderdb.setProducts( new HashSet<>(products));
        orderdb.setUser(user);
        orderdb = ordersRepository.save(orderdb);

        return orderdb;
    }

    private void checkBalance(float newBalance) throws InsufficientBalanceException {
        if(newBalance < 0 ){
            throw new InsufficientBalanceException();
        }
    }

    private void checkProducts(OrderInput order, List<Product> products) throws ProductNotFoundException, ProductAlreadyPurchasedException {
        if(products.stream().distinct().count() != order.getItems().stream().distinct().count()){
            throw new ProductNotFoundException();
        }

        List<Orders> ordersEntity = ordersRepository.findByUser_UserId(order.getUserid());
        Set<Product> p2 = new HashSet<>();
        if(ordersEntity != null){
            ordersEntity.stream().forEach(p-> p2.addAll(p.getProducts()));
        }

        if(p2.size()>0 && hasElementProd(products, p2)){
            throw new ProductAlreadyPurchasedException();
        }
    }
}
