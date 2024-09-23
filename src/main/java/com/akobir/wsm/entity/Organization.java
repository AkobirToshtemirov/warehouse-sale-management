package com.akobir.wsm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cs_orgs")
@Getter
@Setter
@NoArgsConstructor
public class Organization extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "tin", nullable = false)
    private String tin;
}

