package com.bootcamp.java.product.repository;

import com.bootcamp.java.product.domain.ProductType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductTypeRepository  extends ReactiveMongoRepository<ProductType, String> {
    Mono<ProductType> findTopByNameAndActive(String name, Boolean active);
}
