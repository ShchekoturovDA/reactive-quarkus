package com.shchek.pets.repository;

import com.shchek.pets.entity.Filter;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FilterRepository implements PanacheRepository<Filter> {

    public Uni<Filter> findByFilter(Filter filter){
        return find(
                "filterName = ?1 and isReverse = ?2 and filterType = ?3",
                filter.filterName, filter.isReverse, filter.filterType).firstResult();
    }
}
