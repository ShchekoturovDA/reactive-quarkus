package com.shchek.pets.repository;

import com.shchek.pets.entity.Dashboard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DashBoardRepository {

    public Uni<Dashboard> findDashboardById(Long id) {
        return Dashboard.findById(id);
    }
}
