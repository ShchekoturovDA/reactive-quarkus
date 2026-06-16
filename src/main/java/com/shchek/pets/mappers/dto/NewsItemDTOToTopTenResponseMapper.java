package com.shchek.pets.mappers.dto;

import com.shchek.pets.dto.client.NewsItemDto;
import com.shchek.pets.dto.response.NewsResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta")
public abstract class NewsItemDTOToTopTenResponseMapper {

    public abstract NewsResponseDTO toTopTenNewsResponseDTO(NewsItemDto newsItemDto);
}
