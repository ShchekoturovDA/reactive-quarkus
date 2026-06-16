package com.shchek.pets.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "date_filter")
public class DateFilter extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    public Long id;

    @Column(name = "time_before")
    public Long timeBefore;

    @Column(name = "time_after")
    public Long timeAfter;

    @OneToOne(
            mappedBy = "dateFilter",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL}
    )
    public Dashboard dashboard;
}
