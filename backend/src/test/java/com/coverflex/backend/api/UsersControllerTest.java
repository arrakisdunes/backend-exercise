package com.coverflex.backend.api;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import com.coverflex.backend.api.model.User;
import com.coverflex.backend.api.model.UserData;
import com.coverflex.backend.service.UserService;

import static org.mockito.Mockito.*;
class UsersControllerTest {
    @Mock
    UserService userService;
    @Mock
    Logger log;
    @InjectMocks
    UsersController usersController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUser() {
        User user = new User("userId", new UserData(0f, Arrays.<String>asList("String")));
        when(userService.getUser(anyString())).thenReturn(user);

        User result = usersController.getUser("userId");
        Assertions.assertEquals(user, result);
    }

    @Test
    void testGetUser2() {
        usersController.getUser("userId", 0f);
    }
}
