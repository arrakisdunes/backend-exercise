package com.coverflex.backend.service;

import java.util.List;
import com.coverflex.backend.api.model.Product;

public interface ProductsService {
    List<Product> getProducts();
}
