package com.shchek.pets.repository;

import com.shchek.pets.entity.Dashboard;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.reactive.mutiny.Mutiny;

@ApplicationScoped
public class DashBoardRepository extends BaseRepository implements PanacheRepository<Dashboard> {

    public Uni<Dashboard> findByName(String name) {
        return openSession().chain(
                session ->
                        session.createQuery("FROM Dashboard d WHERE d.dashBoardName = :name", Dashboard.class)
                                .setParameter("name", name)
                                .getSingleResultOrNull()
                                .onItem()
                                .ifNotNull()
                                .transformToUni(d ->
                                        Mutiny.fetch(d.filters)
                                                .onItem()
                                                .transform(fetch -> d))
                                .eventually(session::close));
    }

    public Uni<Dashboard> merge(Dashboard dashboard) {
        return openSession().chain(
                session -> session
                        .merge(dashboard)
                        .chain(d ->
                                session.flush()
                                        .onItem().transform(v -> d))
                        .eventually(session::close)
        );
    }
}
