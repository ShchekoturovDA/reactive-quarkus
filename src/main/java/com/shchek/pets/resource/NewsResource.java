package com.shchek.pets.resource;

import com.shchek.pets.dto.response.TopTenNewsResponseDTO;
import com.shchek.pets.service.HackerNewsService;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/haker-news/dementiy")
public class NewsResource {

    @Inject
    HackerNewsService hackerNewsService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/top/{count}")
    public Multi<TopTenNewsResponseDTO> getTopTenNews(Long count) {
        return hackerNewsService.getTopTitles(count);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/random/{count}")
    public Multi<String> getRandoms(Long count) {
        return hackerNewsService.getRandoms(count);
    }
}
