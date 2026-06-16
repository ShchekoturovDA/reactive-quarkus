package com.shchek.pets.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "news_dashboard")
public class Dashboard extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    public Long id;

    @Column(name = "dashboard_name")
    public String dashBoardName;

    @ManyToMany(
            mappedBy = "dashboard"
    )
    @Column(name = "filter_id")
    public Set<Filter> filter;

    @OneToOne(
            mappedBy = "dashboard",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL}
    )
    @Column(name = "date_filter_id")
    public DateFilter dateFilter;

}
