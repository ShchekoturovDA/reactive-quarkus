package com.shchek.pets.repository;

import com.shchek.pets.entity.Filter;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.reactive.mutiny.Mutiny;

@ApplicationScoped
public class FilterRepository extends BaseRepository implements PanacheRepository<Filter> {

    public Uni<Filter> findByFilter(Filter filter) {

        return openSession().chain(
                session -> session.createQuery(
                                "FROM Filter f WHERE f.filterName = :name and f.isReverse = :isReverse and f.filterType = :type",
                                Filter.class)
                        .setParameter("name", filter.filterName)
                        .setParameter("isReverse", filter.isReverse)
                        .setParameter("type", filter.filterType)
                        .getSingleResultOrNull()
                        .onItem()
                        .ifNotNull()
                        .transformToUni(
                                foundFilter ->
                                        Mutiny.fetch(foundFilter.dashboards)
                                                .onItem()
                                                .transform(fetch -> foundFilter))
                        .eventually(session::close)
        );

    }
}
