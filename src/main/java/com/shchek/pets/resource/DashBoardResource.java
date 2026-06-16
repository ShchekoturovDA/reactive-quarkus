package com.shchek.pets.resource;

import com.shchek.pets.dto.request.CreateDashBoardRequest;
import com.shchek.pets.entity.Dashboard;
import com.shchek.pets.service.DashBoardService;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/news-dashboard")
public class DashBoardResource {

    DashBoardService dashBoardService;

    @POST
    @Path("/create")
    public Uni<Dashboard> createDashBoard(Uni<CreateDashBoardRequest> createDashBoardRequestUni) {
        return dashBoardService.createDashBoard(createDashBoardRequestUni);
    }

}
