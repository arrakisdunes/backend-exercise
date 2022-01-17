package com.coverflex.backend.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coverflex.backend.api.model.Product;
import com.coverflex.backend.repository.ProductRepository;
import com.coverflex.backend.service.ProductsService;
import com.coverflex.backend.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductsService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getProducts(){
        List<com.coverflex.backend.repository.entity.Product> productListEntities = new ArrayList<com.coverflex.backend.repository.entity.Product>();
        Iterable<com.coverflex.backend.repository.entity.Product> productIterable = productRepository.findAll();
        productIterable.forEach(productListEntities::add);

        return productMapper.fromRepositoryToModelProductList(productListEntities);
    }
}
