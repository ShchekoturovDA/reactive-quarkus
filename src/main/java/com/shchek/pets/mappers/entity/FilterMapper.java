package com.shchek.pets.mappers.entity;

import com.shchek.pets.dto.request.FilterDTO;
import com.shchek.pets.entity.Filter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "jakarta")
public abstract class FilterMapper {

    @Mapping(target = "dashboards", expression = "java(new ArrayList())")
    public abstract Filter toFilter(FilterDTO filterDTO);

    public abstract List<Filter> toFilters(List<FilterDTO> filterDTOList);
}
