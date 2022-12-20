package com.bootcamp.java.product.service;

import com.bootcamp.java.product.domain.ProductType;
import com.bootcamp.java.product.repository.ProductTypeRepository;
import com.bootcamp.java.product.web.mapper.ProductTypeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ProductTypeMapper productTypeMapper;

    public Flux<ProductType> findAll(){
        log.debug("findAll executed");
        return productTypeRepository.findAll();
    }

    public Mono<ProductType> findById(String id){
        log.debug("findById executed {}", id);
        return productTypeRepository.findById(id);
    }

    public Mono<ProductType> findByName(String name){
        log.debug("findById executed {}", name);
        return productTypeRepository.findTopByNameAndActive(name, true);
    }

    public Mono<ProductType> create(ProductType productType){
        log.debug("create executed {}", productType);
        return productTypeRepository.save(productType);
    }

    public Mono<ProductType> update(String id, ProductType productType) {
        log.debug("update executed {}:{}", id, productType);
        return productTypeRepository.findById(id)
                .flatMap(dbProductType -> {
                    productTypeMapper.update(dbProductType, productType);
                    return productTypeRepository.save(dbProductType);
                });
    }

    public Mono<ProductType> delete(String id){
        log.debug("delete executed {}", id);
        return productTypeRepository.findById(id)
                .flatMap(existingProductType -> productTypeRepository.delete(existingProductType)
                        .then(Mono.just(existingProductType)));
    }

}
