package com.shchek.pets.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "domen_filter")
public class DomenFilter extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    public Long id;

    @Column(name = "domen_name")
    String domenName;

    @Column(name = "is_reverse")
    Boolean isReverse;
}
