package com.shchek.pets.service;

import com.shchek.pets.client.HackerNewsClient;
import com.shchek.pets.dto.response.NewsResponseDTO;
import com.shchek.pets.entity.Dashboard;
import com.shchek.pets.mappers.dto.NewsItemDTOToTopTenResponseMapper;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class HackerNewsService {

    @RestClient
    HackerNewsClient hackerNewsClient;

    @Inject
    DashBoardService dashBoardService;

    @Inject
    NewsItemDTOToTopTenResponseMapper newsItemDTOToTopTenResponseMapper;

    public Multi<NewsResponseDTO> getTopTitles(long count) {
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

    protected Uni<NewsResponseDTO> getTitleById(Long id) {
        return hackerNewsClient.getItemById(id)
                .map(
                        news -> newsItemDTOToTopTenResponseMapper.toTopTenNewsResponseDTO(news));
    }

    public Multi<NewsResponseDTO> getTopTitlesByDashboards(Uni<List<String>> dashboardsNames, Long depth) {
        Multi<Dashboard> dashboardMulti =
                dashBoardService.getDashBoardsByName(
                dashboardsNames.onItem()
                        .transformToMulti(
                                names ->
                                        Multi.createFrom().iterable(names))
                );

        Multi<NewsResponseDTO> newsResponseDTOMulti = getTopTitles(depth);
        newsResponseDTOMulti.onItem()
    }
}
