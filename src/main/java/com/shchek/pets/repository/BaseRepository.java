package com.shchek.pets.repository;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.hibernate.reactive.mutiny.Mutiny;

public class BaseRepository {

    @Inject
    Mutiny.SessionFactory sessionFactory;

    protected Uni<Mutiny.Session> openSession() {
        return sessionFactory.openSession();

    }
}
