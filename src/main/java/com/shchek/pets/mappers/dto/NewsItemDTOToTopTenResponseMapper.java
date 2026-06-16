package com.shchek.pets.mappers.dto;

import com.shchek.pets.dto.client.NewsItemDto;
import com.shchek.pets.dto.response.TopTenNewsResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta")
public abstract class NewsItemDTOToTopTenResponseMapper {

    public abstract TopTenNewsResponseDTO toTopTenNewsResponseDTO(NewsItemDto newsItemDto);
}
