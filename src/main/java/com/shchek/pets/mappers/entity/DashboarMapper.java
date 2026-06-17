package com.shchek.pets.mappers.entity;

import com.shchek.pets.dto.request.CreateDashBoardRequest;
import com.shchek.pets.entity.Dashboard;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "jakarta",
        uses = FilterMapper.class)
public abstract class DashboarMapper {

    FilterMapper filterMapper = Mappers.getMapper(FilterMapper.class);

    public abstract Dashboard toDashboard(CreateDashBoardRequest createDashBoardRequest);

}
