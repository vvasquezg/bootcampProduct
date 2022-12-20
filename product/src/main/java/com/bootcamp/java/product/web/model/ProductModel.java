package com.bootcamp.java.product.web.model;

import com.bootcamp.java.product.domain.ProductType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {
    @JsonIgnore
    private String id;
    @NotBlank(message = "Name cannot be null or empty")
    private String name;
    @NotBlank(message = "Code cannot be null or empty")
    private String code;
    private ProductType productType;
    private Boolean active;
}
