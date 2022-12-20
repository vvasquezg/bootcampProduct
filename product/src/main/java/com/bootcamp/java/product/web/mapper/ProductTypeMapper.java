package com.bootcamp.java.product.web.mapper;

import com.bootcamp.java.product.domain.ProductType;
import com.bootcamp.java.product.web.model.ProductTypeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductTypeMapper {
    ProductType modelToEntity(ProductTypeModel model);

    ProductTypeModel entityToModel(ProductType event);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget ProductType entity, ProductType updateEntity);
}
