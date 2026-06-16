package com.shchek.pets.service;

import com.shchek.pets.dto.request.CreateDashBoardRequest;
import com.shchek.pets.entity.Dashboard;
import com.shchek.pets.entity.Filter;
import com.shchek.pets.mappers.entity.DashboarMapper;
import com.shchek.pets.mappers.entity.FilterMapper;
import com.shchek.pets.repository.DashBoardRepository;
import com.shchek.pets.repository.FilterRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Request;

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
    @Inject
    Request request;

    public Uni<Dashboard> createDashBoard(Uni<CreateDashBoardRequest> createDashBoardRequestUni) {
        Uni<Dashboard> dashboardUni =
                createDashBoardRequestUni.onItem()
                        .transformToUni(
                                request ->
                                        dashboarMapper.toDashboard(request));

        Multi<Filter> filterMulti =
                createDashBoardRequestUni
                        .onItem()
                        .transformToMulti(
                                request ->
                                        Multi.createFrom().iterable(request.getFilters()))
                        .onItem().transformToUniAndMerge(
                                filterDTO ->
                                        filterMapper.toFilter(filterDTO));

        filterMulti.onItem()
                .transformToUniAndMerge(
                        filter ->
                                filterRepository.findByFilter(filter)
                                        .onFailure().recoverWithItem(filter))
                .onItem()
                .transformToUni(
                        filter ->
                                dashboardUni.map(
                                        dashboard ->
                                                dashboard.filter.add(filter)));

        return dashboardUni
                .onItem().call(
                        dashboard ->
                                dashBoardRepository.persist(dashboard));

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
