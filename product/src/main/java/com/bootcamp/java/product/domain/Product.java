package com.bootcamp.java.product.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = { "name" })
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "product")
public class Product {
    @Id
    private String id;
    @NotNull
    private String name;
    @NotNull
    @Indexed(unique = true)
    private String code;
    @NotNull
    private ProductType productType;
    @NotNull
    private Boolean active;
}
