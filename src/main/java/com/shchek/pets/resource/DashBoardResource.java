package com.shchek.pets.resource;

import com.shchek.pets.dto.request.CreateDashBoardRequest;
import com.shchek.pets.service.DashBoardService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/news-dashboard")
public class DashBoardResource {

    @Inject
    DashBoardService dashBoardService;

    @POST
    @Path("/create")
    public Uni<Long> createDashBoard(CreateDashBoardRequest createDashBoardRequestUni) {
        return dashBoardService.createDashBoard(createDashBoardRequestUni);
    }

}
