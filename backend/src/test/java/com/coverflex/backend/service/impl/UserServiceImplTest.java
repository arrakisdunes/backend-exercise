package com.coverflex.backend.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.coverflex.backend.api.model.UserData;
import com.coverflex.backend.repository.OrdersRepository;
import com.coverflex.backend.repository.UserRepository;
import com.coverflex.backend.repository.entity.Orders;
import com.coverflex.backend.repository.entity.Product;
import com.coverflex.backend.repository.entity.User;

import static org.mockito.Mockito.*;
class UserServiceImplTest {
    @Mock
    OrdersRepository ordersRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userServiceImpl;

    public static final Product PRODUCT = new Product("String", "name", 10f);
    public static final User USER = new User(Long.valueOf(1), "userId", 10f);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testGetUser_save() {
        when(ordersRepository.findByUser_UserId(anyString())).thenReturn(Arrays.<Orders>asList(new Orders(Long.valueOf(1), 0f, new HashSet<Product>(Arrays.asList(PRODUCT)), USER)));
        when(userRepository.findByUserId(anyString())).thenReturn(null);

        com.coverflex.backend.api.model.User result = userServiceImpl.getUser("userId");
        Assertions.assertEquals("userId", result.getUserId());
        Assertions.assertEquals(0f, result.getData().getBalance());
    }

    @Test
    void testGetUser_existing() {
        when(ordersRepository.findByUser_UserId(anyString())).thenReturn(Arrays.<Orders>asList(new Orders(Long.valueOf(1), 0f, new HashSet<Product>(Arrays.asList(PRODUCT)), USER)));
        User user = new User( "userId", 10f);
        Optional<User> userEntity = Optional.of(user);

        when(userRepository.findByUserId(anyString())).thenReturn(userEntity);


        com.coverflex.backend.api.model.User result = userServiceImpl.getUser("userId");
        Assertions.assertEquals("userId", result.getUserId());
        Assertions.assertEquals(10f, result.getData().getBalance());
    }

    @Test
    void testGetOrCreate() {
        when(userRepository.findByUserId(anyString())).thenReturn(null);

        User result = userServiceImpl.getOrCreate("userId");
        Assertions.assertEquals("userId", result.getUserId());
        Assertions.assertEquals(0f, result.getBalance());
    }

    @Test
    void testUpdateBalance() {
        when(ordersRepository.findByUser_UserId(anyString())).thenReturn(Arrays.<Orders>asList(new Orders(Long.valueOf(1), 0f, new HashSet<Product>(Arrays.asList(PRODUCT)), USER)));
        User user = new User( "userId", 10f);
        Optional<User> userEntity = Optional.of(user);

        when(userRepository.findByUserId(anyString())).thenReturn(userEntity);
        userServiceImpl.updateBalance("userId", 20f);

        com.coverflex.backend.api.model.User result = userServiceImpl.getUser("userId");
        Assertions.assertEquals("userId", result.getUserId());
        Assertions.assertEquals(20f, result.getData().getBalance());
    }
}