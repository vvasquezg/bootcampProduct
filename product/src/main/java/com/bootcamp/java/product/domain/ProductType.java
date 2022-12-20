package com.bootcamp.java.product.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = { "name" })
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "productType")
public class ProductType {
    @Id
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String code;
    @NotNull
    private Boolean active;
}
