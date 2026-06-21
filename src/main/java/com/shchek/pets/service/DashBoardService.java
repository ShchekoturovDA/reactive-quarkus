package com.shchek.pets.service;

import com.shchek.pets.dto.request.CreateDashBoardRequest;
import com.shchek.pets.entity.Dashboard;
import com.shchek.pets.entity.Filter;
import com.shchek.pets.mappers.entity.DashboarMapper;
import com.shchek.pets.repository.DashBoardRepository;
import com.shchek.pets.repository.FilterRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class DashBoardService {

    @Inject
    DashBoardRepository dashBoardRepository;

    @Inject
    FilterRepository filterRepository;

    @Inject
    DashboarMapper dashboarMapper;

    public Uni<String> createDashBoard(CreateDashBoardRequest createDashBoardRequest) {
        Uni<CreateDashBoardRequest> createDashBoardRequestUni = Uni.createFrom().item(createDashBoardRequest);
        Uni<Dashboard> dashboardUni =
                createDashBoardRequestUni.onItem()
                        .transformToUni(
                                request ->
                                        Uni.createFrom().item(dashboarMapper.toDashboard(request)));

        Uni<List<Filter>> filters = dashboardUni
                .onItem()
                .transformToMulti(dashboard ->
                        Multi.createFrom().iterable(dashboard.filters)
                                .onItem()
                                .transformToUniAndMerge(filter ->
                                        filterRepository.findByFilter(filter)
                                                .onItem()
                                                .ifNull()
                                                .continueWith(filter)
                                                .onItem()
                                                .transform(resolvedFilter -> {
                                                    resolvedFilter.dashboards.add(dashboard);
                                                    return resolvedFilter;
                                                })
                                )
                ).collect()
                .asList();
        return filters.onItem()
                .transformToUni(
                        list ->
                                dashboardUni.call(
                                                dashboard -> {
                                                    dashboard.filters = list;
                                                    return saveDashBoard(dashboard);
                                                })
                                        .map(savedDashboard -> savedDashboard.dashBoardName)
                );
    }

    Uni<Dashboard> saveDashBoard(Dashboard dashboard) {
        return dashBoardRepository.merge(dashboard);
    }

    public Multi<Dashboard> getDashBoardsByName(Multi<String> names) {
        return names.onItem()
                .transformToUniAndMerge(
                        name -> findByName(name)
                                .onFailure().call(throwable -> {
                                    System.out.println("failed to get dashboard named: " + name);
                                    System.out.println("ERROR: " + throwable.getMessage());
                                    throw new RuntimeException(throwable);
                                }));
    }

    public Uni<Dashboard> findByName(String name) {
        return dashBoardRepository.findByName(name);
    }
}
