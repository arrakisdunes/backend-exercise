package com.coverflex.backend.integrationtest.rest;

import java.util.Arrays;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.coverflex.backend.api.model.NewOrderInput;
import com.coverflex.backend.api.model.OrderInput;
import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "36000")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrdersRestService {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getOrders() throws JsonProcessingException {
        getNewOrderInput();
    }

    @Test
    public void getOrdersDuplicate() throws JsonProcessingException {
        NewOrderInput in = getNewOrderInput();

        in.setOrder(new OrderInput(Arrays.asList("product-1"),"artur"));
        EntityExchangeResult<String> resultError = this.webTestClient.post().uri("/api/orders").body(Mono.just(in), NewOrderInput.class).exchange().expectStatus().is4xxClientError().expectBody(String.class).returnResult();
        Assertions.assertEquals(true, resultError.getResponseBody().equals("{\"error\":\"products_already_purchased\"}"));

    }

    @Test
    public void getOrdersBalanceError() throws JsonProcessingException {
        NewOrderInput in = getNewOrderInput();

        in.setOrder(new OrderInput(Arrays.asList("product-3"),"artur"));
        EntityExchangeResult<String> resultError = this.webTestClient.post().uri("/api/orders").body(Mono.just(in), NewOrderInput.class).exchange().expectStatus().is4xxClientError().expectBody(String.class).returnResult();
        Assertions.assertEquals(true, resultError.getResponseBody().equals("{\"error\":\"insufficient_balance\"}"));

    }

    @Test
    public void getOrdersProductNotFoundError() throws JsonProcessingException {
        NewOrderInput in = getNewOrderInput();

        in.setOrder(new OrderInput(Arrays.asList("product-NotFound"),"artur"));
        EntityExchangeResult<String> resultError = this.webTestClient.post().uri("/api/orders").body(Mono.just(in), NewOrderInput.class).exchange().expectStatus().is4xxClientError().expectBody(String.class).returnResult();
        Assertions.assertEquals(true, resultError.getResponseBody().equals("{\"error\":\"products_not_found\"}"));

    }

    private NewOrderInput getNewOrderInput() {
        NewOrderInput in = new NewOrderInput();
        in.setOrder(new OrderInput(Arrays.asList("product-1", "product-2"), "artur"));
        EntityExchangeResult<String> result = this.webTestClient.post().uri("/api/orders").body(Mono.just(in), NewOrderInput.class).exchange().expectStatus().isOk().expectBody(String.class).returnResult();
        Assertions.assertEquals(true, result.getResponseBody().equals("{\"order\":{\"data\":{\"items\":[\"product-1\",\"product-2\"],\"total\":19.0},\"order_id\":\"1\"}}"));
        return in;
    }
}
