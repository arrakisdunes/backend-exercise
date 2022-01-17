package com.coverflex.backend.api.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * "order": {"items": ["product-1", "product-2"], "user_id": "johndoe"}}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderInput {

    private List<String> items;

    @JsonProperty("user_id")
    private String userid;
}
