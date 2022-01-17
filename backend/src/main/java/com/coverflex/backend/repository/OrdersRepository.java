package com.coverflex.backend.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.coverflex.backend.repository.entity.Orders;
import com.coverflex.backend.repository.entity.Product;
import com.coverflex.backend.repository.entity.User;

public interface OrdersRepository extends CrudRepository<Orders, Long> {
    List<Orders> findByUser_UserId(String userId);
}
