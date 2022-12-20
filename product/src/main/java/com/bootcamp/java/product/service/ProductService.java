package com.bootcamp.java.product.service;

import com.bootcamp.java.product.domain.Product;
import com.bootcamp.java.product.repository.ProductRepository;
import com.bootcamp.java.product.service.exception.InvalidProductTypeException;
import com.bootcamp.java.product.web.mapper.ProductMapper;
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
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductTypeService productTypeService;

    public Flux<Product> findAll(){
        log.debug("findAll executed");
        return productRepository.findAll();
    }

    public Mono<Product> findById(String id){
        log.debug("findById executed {}", id);
        return productRepository.findById(id);
    }

    public Mono<Product> findByName(String name){
        log.debug("findByName executed {}", name);
        return productRepository.findTopByNameAndActive(name, true);
    }

    public Mono<Product> findByCode(String code){
        log.debug("findByCode executed {}", code);
        return productRepository.findTopByCodeAndActive(code, true);
    }

    public Mono<Product> create(Product product){
        log.debug("create executed {}", product);

        return productTypeService.findByName(product.getProductType().getName())
                .switchIfEmpty(Mono.error(new InvalidProductTypeException()))
                .flatMap(productType -> {
                    product.setProductType(productType);
                    return productRepository.save(product);
                });
    }

    public Mono<Product> update(String id, Product product) {
        log.debug("update executed {}:{}", id, product);
        return productRepository.findById(id)
                .flatMap(dbProduct -> productTypeService.findByName(product.getProductType().getName())
                                .switchIfEmpty(Mono.error(new InvalidProductTypeException()))
                                        .flatMap(productType -> {
                                            product.setProductType(productType);
                                            productMapper.update(dbProduct, product);
                                            return productRepository.save(dbProduct);
                                        }));
    }

    public Mono<Product> delete(String id){
        log.debug("delete executed {}", id);
        return productRepository.findById(id)
                .flatMap(existingProduct -> productRepository.delete(existingProduct)
                        .then(Mono.just(existingProduct)));
    }
}
