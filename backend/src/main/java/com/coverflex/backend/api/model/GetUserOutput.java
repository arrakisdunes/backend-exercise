package com.coverflex.backend.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * {"user": {"user_id": "johndoe", "data": {"balance": 500, "product_ids": [...]}}}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetUserOutput {

   private User user;
}
