package com.shchek.pets.resource;

import com.shchek.pets.dto.response.NewsResponseDTO;
import com.shchek.pets.service.HackerNewsService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/haker-news/dementiy")
public class NewsResource {

    @Inject
    HackerNewsService hackerNewsService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/top/{count}")
    public Multi<NewsResponseDTO> getTopTenNews(Long count) {
        return hackerNewsService.getTopTitles(count);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/top/by-dashboards/{deep}")
    public Multi<NewsResponseDTO> getRandoms(Uni<List<String>> dashboardNames, Long deep) {
        return hackerNewsService.getTopTitlesByDashboards(dashboardNames, deep);
    }
}
