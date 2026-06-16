package com.shchek.pets.service;

import com.shchek.pets.client.HackerNewsClient;
import com.shchek.pets.dto.response.TopTenNewsResponseDTO;
import com.shchek.pets.mappers.dto.NewsItemDTOToTopTenResponseMapper;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class HackerNewsService {

    @RestClient
    HackerNewsClient hackerNewsClient;

    @Inject
    NewsItemDTOToTopTenResponseMapper newsItemDTOToTopTenResponseMapper;

    public Multi<TopTenNewsResponseDTO> getTopTitles(long count){
        return hackerNewsClient.getTopStories()
                .onItem()
                .transformToMulti(
                        ids ->
                                Multi.createFrom().iterable(
                                        ids.stream().limit(count).toList()))
                .onItem()
                .transformToUniAndMerge(
                        id -> getTitleById(id));
    }

    protected Uni<TopTenNewsResponseDTO> getTitleById(Long id) {
        return hackerNewsClient.getItemById(id)
                .map(
                        news -> newsItemDTOToTopTenResponseMapper.toTopTenNewsResponseDTO(news));
    }

    public Multi<String> getRandoms(Long count) {
        return null;
    }
}
