package com.bootcamp.java.product.web;

import com.bootcamp.java.product.domain.Product;
import com.bootcamp.java.product.service.ProductService;
import com.bootcamp.java.product.web.mapper.ProductMapper;
import com.bootcamp.java.product.web.model.ProductModel;
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
@RequestMapping("/v1/product")
public class ProductController {
    @Value("${spring.application.name}")
    String name;

    @Value("${server.port}")
    String port;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<ProductModel>>> getAll(){
        log.info("getAll executed");

        return Mono.just(ResponseEntity.ok()
                .body(productService.findAll()
                        .map(customer -> productMapper.entityToModel(customer))));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductModel>> getById(@PathVariable String id){
        log.info("getById executed {}", id);
        Mono<Product> response = productService.findById(id);
        return response
                .map(customer -> productMapper.entityToModel(customer))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/getByName/{name}")
    public Mono<ResponseEntity<ProductModel>> getByName(@PathVariable String name){
        log.info("getByName executed {}", name);
        Mono<Product> response = productService.findByName(name);
        return response
                .map(customer -> productMapper.entityToModel(customer))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/getByCode/{code}")
    public Mono<ResponseEntity<ProductModel>> getByCode(@PathVariable String code){
        log.info("getByCode executed {}", code);
        Mono<Product> response = productService.findByCode(code);
        return response
                .map(customer -> productMapper.entityToModel(customer))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ProductModel>> create(@Valid @RequestBody ProductModel request){
        log.info("create executed {}", request);
        return productService.create(productMapper.modelToEntity(request))
                .map(customer -> productMapper.entityToModel(customer))
                .flatMap(c ->
                        Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
                                        port, "product", c.getId())))
                                .body(c)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductModel>> updateById(@PathVariable String id, @Valid @RequestBody ProductModel request){
        log.info("updateById executed {}:{}", id, request);
        return productService.update(id, productMapper.modelToEntity(request))
                .map(customer -> productMapper.entityToModel(customer))
                .flatMap(c ->
                        Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
                                        port, "product", c.getId())))
                                .body(c)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
        log.info("deleteById executed {}", id);
        return productService.delete(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
