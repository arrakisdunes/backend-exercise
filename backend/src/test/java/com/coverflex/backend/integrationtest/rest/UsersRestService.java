package com.coverflex.backend.integrationtest.rest;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "36000")
public class UsersRestService {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getUsers() throws JsonProcessingException {
        //Test a existing user
        EntityExchangeResult<String> result = this.webTestClient.get().uri("/api/users/artur").exchange().expectStatus().isOk().expectBody(String.class).returnResult();
        Assertions.assertEquals(true, result.getResponseBody().contains("artur"));
        Assertions.assertEquals(true, result.getResponseBody().contains("40"));

        //Test a new user
        EntityExchangeResult<String> resultNewSave = this.webTestClient.get().uri("/api/users/t1").exchange().expectStatus().isOk().expectBody(String.class).returnResult();
        Assertions.assertEquals(true, resultNewSave.getResponseBody().contains("t1"));
        Assertions.assertEquals(true, resultNewSave.getResponseBody().contains("20"));
    }
}
