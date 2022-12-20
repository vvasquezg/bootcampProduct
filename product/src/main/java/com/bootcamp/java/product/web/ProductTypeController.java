package com.bootcamp.java.product.web;

import com.bootcamp.java.product.domain.ProductType;
import com.bootcamp.java.product.service.ProductTypeService;
import com.bootcamp.java.product.web.mapper.ProductTypeMapper;
import com.bootcamp.java.product.web.model.ProductTypeModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/productType")
public class ProductTypeController {
    @Value("${spring.application.name}")
    String name;

    @Value("${server.port}")
    String port;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<ProductTypeModel>>> getAll(){
        log.info("getAll executed");

        return Mono.just(ResponseEntity.ok()
                .body(productTypeService.findAll()
                        .map(customer -> productTypeMapper.entityToModel(customer))));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductTypeModel>> getById(@PathVariable String id){
        log.info("getById executed {}", id);
        Mono<ProductType> response = productTypeService.findById(id);
        return response
                .map(customer -> productTypeMapper.entityToModel(customer))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByName/{name}")
    public Mono<ResponseEntity<ProductTypeModel>> getByName(@PathVariable String name){
        log.info("getById executed {}", name);
        Mono<ProductType> response = productTypeService.findByName(name);
        return response
                .map(customer -> productTypeMapper.entityToModel(customer))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ProductTypeModel>> create(@Valid @RequestBody ProductTypeModel request){
        log.info("create executed {}", request);
        return productTypeService.create(productTypeMapper.modelToEntity(request))
                .map(customer -> productTypeMapper.entityToModel(customer))
                .flatMap(c ->
                        Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
                                        port, "productType", c.getId())))
                                .body(c)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductTypeModel>> updateById(@PathVariable String id, @Valid @RequestBody ProductTypeModel request){
        log.info("updateById executed {}:{}", id, request);
        return productTypeService.update(id, productTypeMapper.modelToEntity(request))
                .map(customer -> productTypeMapper.entityToModel(customer))
                .flatMap(c ->
                        Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
                                        port, "customer", c.getId())))
                                .body(c)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
        log.info("deleteById executed {}", id);
        return productTypeService.delete(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
