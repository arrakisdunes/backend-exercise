package com.coverflex.backend.service;

import java.util.List;
import com.coverflex.backend.api.model.Product;
import com.coverflex.backend.api.model.User;

public interface UserService {
    User getUser(String userId);
    com.coverflex.backend.repository.entity.User getOrCreate(String userId);
    void updateBalance(String userId, float balance);
}
