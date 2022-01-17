package com.coverflex.backend.api.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {"order": {"order_id": "123", "data": {"items": [...], "total": 500}}}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderData {

    private List<String> items;
    private float total;
}
