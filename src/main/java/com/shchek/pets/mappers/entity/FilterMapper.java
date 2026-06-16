package com.shchek.pets.mappers.entity;

import com.shchek.pets.dto.request.FilterDTO;
import com.shchek.pets.entity.Filter;
import io.smallrye.mutiny.Uni;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta")
public abstract class FilterMapper {

    public abstract Uni<Filter> toFilter(FilterDTO filterDTO);
}
