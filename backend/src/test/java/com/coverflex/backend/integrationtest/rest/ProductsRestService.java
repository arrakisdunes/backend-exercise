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
public class ProductsRestService {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getAllProducts() throws JsonProcessingException {
        EntityExchangeResult<String> result = this.webTestClient.get().uri("/api/products").exchange().expectStatus().isOk().expectBody(String.class).returnResult();
        Assertions.assertEquals(true, result.getResponseBody().equals("{\"products\":[{\"id\":\"product-1\",\"name\":\"product-1\",\"price\":9.0},{\"id\":\"product-2\",\"name\":\"product-2\",\"price\":10.0},{\"id\":\"product-3\",\"name\":\"product-3\",\"price\":30.0}]}"));
    }
}
