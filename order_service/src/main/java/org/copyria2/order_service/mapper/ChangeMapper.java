package org.copyria2.order_service.mapper;

import liquibase.change.Change;
import org.copyria2.order_service.client.rest.model.ResponseChangeDto;
import org.copyria2.order_service.dto.ChangeDto;
import org.copyria2.order_service.entity.ChangeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ChangeMapper {
    @Mapping(target = "currency", source = "ccy")
    @Mapping(target = "base_currency", source = "base_ccy")
    ChangeEntity toChangeEntity(ChangeDto dto);
    @Mapping(target = "ccy", source = "currency")
    @Mapping(target = "baseCcy", source = "base_currency")
    ResponseChangeDto toChangeDto(ChangeEntity entity);
}
