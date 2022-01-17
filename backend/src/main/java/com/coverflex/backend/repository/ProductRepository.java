package com.coverflex.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.coverflex.backend.repository.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query( "select o from Product o where id in :ids" )
    List<Product> findByIds(@Param("ids") List<String> ids);
}
