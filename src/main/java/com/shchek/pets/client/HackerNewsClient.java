package com.shchek.pets.client;

import com.shchek.pets.dto.client.NewsItemDto;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/v0")
@RegisterRestClient(configKey = "hacker-url")
public interface HackerNewsClient {

    @GET
    @Path("item/{id}.json")
    Uni<NewsItemDto> getItemById(Long id);

    @GET
    @Path("topstories.json")
    Uni<List<Long>> getTopStories();

    @GET
    @Path("maxitem.json")
    Uni<Long> getMaxItem();
}
