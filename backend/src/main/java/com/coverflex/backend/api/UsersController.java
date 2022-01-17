package com.coverflex.backend.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.coverflex.backend.api.model.User;
import com.coverflex.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = {"/api/users"}, produces = {APPLICATION_JSON_VALUE})
@Slf4j
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public User getUser(@PathVariable(value = "userId") String userId) {
        log.info("Start Get /api/users/"+userId);
        return userService.getUser(userId);
    }

    @PutMapping("/balance/{userId}")
    public void getUser(@PathVariable(value = "userId") String userId, @RequestParam float balance) {
        log.info("Start Get /api/users/balance"+userId+"?balance="+balance);
       userService.updateBalance(userId, balance);
    }
}
