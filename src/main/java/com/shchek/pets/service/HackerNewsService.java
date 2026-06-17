package com.shchek.pets.service;

import com.shchek.pets.client.HackerNewsClient;
import com.shchek.pets.dto.response.NewsResponseDTO;
import com.shchek.pets.entity.Filter;
import com.shchek.pets.mappers.dto.NewsItemDTOToTopTenResponseMapper;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

import static com.shchek.pets.entity.FilterType.AUTHOR;

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
                        news -> newsItemDTOToTopTenResponseMapper.toNewsResponseDTO(news));
    }

    public Multi<NewsResponseDTO> getTopTitlesByDashboards(List<String> dashboardsNames, Long depth) {
        Multi<Filter> filterMulti =
                dashBoardService.getDashBoardsByName(
                                Uni.createFrom()
                                        .item(dashboardsNames).onItem()
                                        .transformToMulti(
                                                names ->
                                                        Multi.createFrom().iterable(names))
                        ).onItem()
                        .transformToMultiAndMerge(
                                dashboard ->
                                        Multi.createFrom().iterable(dashboard.filters));

        return getTopTitles(depth).filter(
                news ->
                        filterMulti.filter(
                                        filter ->
                                                filter.filterType.equals(AUTHOR.name())
                                                        && filter.filterName.equals(news.getAuthor()))
                                .collect().asList()
                                .map(List::isEmpty)
                                .await()
                                .indefinitely());
    }
}
