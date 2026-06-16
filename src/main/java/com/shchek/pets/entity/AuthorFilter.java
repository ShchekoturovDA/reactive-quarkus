package com.shchek.pets.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "author_filter")
public class AuthorFilter extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    public Long id;

    @Column(name = "author_name")
    String authorName;

    @Column(name = "is_reverse")
    Boolean isReverse;
}
