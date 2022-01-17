package com.coverflex.backend.service;

import com.coverflex.backend.api.model.OrderInput;
import com.coverflex.backend.api.model.OrderOutput;
import com.coverflex.backend.api.model.User;
import com.coverflex.backend.exceptions.BaseException;

public interface OrdersService {
    OrderOutput saveOrder(OrderInput order) throws BaseException;
}
