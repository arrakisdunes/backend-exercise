package com.coverflex.backend.service.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.coverflex.backend.api.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product fromRepositoryToModelProduct(com.coverflex.backend.repository.entity.Product input);
    List<Product> fromRepositoryToModelProductList(List<com.coverflex.backend.repository.entity.Product> input);
}
