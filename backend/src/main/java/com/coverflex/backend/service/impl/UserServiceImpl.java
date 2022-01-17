package com.coverflex.backend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.coverflex.backend.api.model.Product;
import com.coverflex.backend.api.model.User;
import com.coverflex.backend.api.model.UserData;
import com.coverflex.backend.repository.OrdersRepository;
import com.coverflex.backend.repository.ProductRepository;
import com.coverflex.backend.repository.UserRepository;
import com.coverflex.backend.service.UserService;
import com.coverflex.backend.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private UserRepository userRepository;

    @Value("${user.init.balance:0}")
    private float initBalance;

    @Override
    public User getUser(String userId) {
        return map(getOrCreate(userId));
    }

    @Override
    public com.coverflex.backend.repository.entity.User getOrCreate(String userId){
        Optional<com.coverflex.backend.repository.entity.User> userEntity = userRepository.findByUserId(userId);
        com.coverflex.backend.repository.entity.User newUser = new com.coverflex.backend.repository.entity.User(userId, initBalance);

        if(userEntity == null || !userEntity.isPresent()) {
            userRepository.save(newUser);
        }else{
            newUser = userEntity.get();
        }

        return newUser;
    }

    @Transactional
    @Override
    public void updateBalance(String userId, float balance){
        Optional<com.coverflex.backend.repository.entity.User> userEntity = userRepository.findByUserId(userId);
        if(userEntity.isPresent()){
            com.coverflex.backend.repository.entity.User user = userEntity.get();
            user.setBalance(balance);
            userRepository.save(user);
        }
    }

    private User map(com.coverflex.backend.repository.entity.User userEntity){
        User user = new User();
        user.setUserId(userEntity.getUserId());

        List<com.coverflex.backend.repository.entity.Orders> ordersEntity = ordersRepository.findByUser_UserId(userEntity.getUserId());
        List<String> productsIds = new ArrayList<>();

        if(ordersEntity!=null) {
            ordersEntity.forEach(o -> o.getProducts().forEach(c -> productsIds.add(c.getId())));
        }

        UserData data = new UserData(userEntity.getBalance(), productsIds);
        user.setData(data);

        return user;
    }
}
