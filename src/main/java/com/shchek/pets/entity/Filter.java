package com.shchek.pets.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "domen_filter")
public class Filter extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    public Long id;

    @Column(name = "domen_name")
    public String filterName;

    @Column(name = "is_reverse")
    public Boolean isReverse;

    @Column(name = "filter_type")
    public String filterType;
}
