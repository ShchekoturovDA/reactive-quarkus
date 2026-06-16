package com.shchek.pets.repository;

import com.shchek.pets.entity.Dashboard;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DashBoardRepository implements PanacheRepository<Dashboard> {

    public Uni<Dashboard> findByName(String name) {
        return find("name = ?1", name).firstResult();
    }

}
