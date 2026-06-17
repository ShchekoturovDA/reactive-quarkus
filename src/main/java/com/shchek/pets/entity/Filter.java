package com.shchek.pets.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "filter_table")
public class Filter extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name_to_filter")
    public String filterName;

    @Column(name = "is_reverse")
    public Boolean isReverse;

    @Column(name = "filter_type")
    public String filterType;

    @ManyToMany(mappedBy = "filters")
    public List<Dashboard> dashboards;
}
