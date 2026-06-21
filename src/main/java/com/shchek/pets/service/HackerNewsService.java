package com.shchek.pets.service;

import com.shchek.pets.client.HackerNewsClient;
import com.shchek.pets.dto.response.NewsResponseDTO;
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
        Multi<String> filterNames = dashBoardService
                .getDashBoardsByName(
                        Multi.createFrom().iterable(dashboardsNames))
                .onItem()
                .transformToMultiAndMerge(
                        dashboard ->
                                Multi.createFrom().iterable(dashboard.filters))
                .filter(
                        filter ->
                                !filter.isReverse && filter.filterType.equals(AUTHOR.name()))
                .onItem()
                .transform(filter -> filter.filterName)
                .select()
                .distinct();


        Multi<NewsResponseDTO> topNews = getTopTitles(depth);

        return topNews.onItem().transformToUniAndMerge(
                        news ->
                                filterNames.select().where(
                                                fName ->
                                                        fName.equals(news.getAuthor()))
                                        .toUni()
                                        .onItem()
                                        .transform(
                                                first -> {
                                                    news.setShow(first != null);
                                                    return news;
                                                }))
                .select()
                .where(NewsResponseDTO::isShow);
    }
}

