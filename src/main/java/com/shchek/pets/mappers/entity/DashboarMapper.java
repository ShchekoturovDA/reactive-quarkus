package com.shchek.pets.mappers.entity;

import com.shchek.pets.dto.request.CreateDashBoardRequest;
import com.shchek.pets.entity.Dashboard;
import io.smallrye.mutiny.Uni;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta")
public abstract class DashboarMapper {

    public abstract Uni<Dashboard> toDashboard(CreateDashBoardRequest createDashBoardRequest);

}
