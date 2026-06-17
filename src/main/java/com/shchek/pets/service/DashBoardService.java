package com.shchek.pets.service;

import com.shchek.pets.dto.request.CreateDashBoardRequest;
import com.shchek.pets.entity.Dashboard;
import com.shchek.pets.mappers.entity.DashboarMapper;
import com.shchek.pets.mappers.entity.FilterMapper;
import com.shchek.pets.repository.DashBoardRepository;
import com.shchek.pets.repository.FilterRepository;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DashBoardService {

    @Inject
    DashBoardRepository dashBoardRepository;

    @Inject
    FilterRepository filterRepository;

    @Inject
    FilterMapper filterMapper;

    @Inject
    DashboarMapper dashboarMapper;

    public Uni<Long> createDashBoard(CreateDashBoardRequest createDashBoardRequest) {
        Uni<CreateDashBoardRequest> createDashBoardRequestUni = Uni.createFrom().item(createDashBoardRequest);
        Uni<Dashboard> dashboardUni =
                createDashBoardRequestUni.onItem()
                        .transformToUni(
                                request ->
                                        Uni.createFrom().item(dashboarMapper.toDashboard(request)));

        dashboardUni
                .onItem()
                .transformToMulti(
                        dasboard ->
                                Multi.createFrom().iterable(dasboard.filters))
                .onItem()
                .transformToUniAndConcatenate(
                        filter ->
                                filterRepository.findByFilter(filter))
                .onItem()
                .transform(
                        filter ->
                                dashboardUni.map(
                                        dashboard -> {
                                            filter.dashboards.add(dashboard);
                                            dashboard.filters.add(filter);
                                            return dashboard;
                                        }));

        return dashboardUni
                .onItem().call(
                        dashboard ->
                                saveDashBoard(dashboard)).map(savedDashboard -> savedDashboard.id);

    }

    @WithTransaction
    Uni<Dashboard> saveDashBoard(Dashboard dashboard) {
        return dashBoardRepository.persist(dashboard);
    }

    public Multi<Dashboard> getDashBoardsByName(Multi<String> names) {
        return names.onItem()
                .transformToUniAndMerge(
                        name ->
                                dashBoardRepository.findByName(name).onFailure().call(throwable -> {
                                    System.out.println("failed to get dashboard named: " + name);
                                    System.out.println("ERROR: " + throwable.getMessage());
                                    throw new RuntimeException(throwable);
                                }));
    }
}
