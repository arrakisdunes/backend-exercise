package com.coverflex.backend.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.coverflex.backend.repository.entity.User;

public interface UserRepository  extends CrudRepository<User, Long> {
    Optional<User> findByUserId(String userId);
}
