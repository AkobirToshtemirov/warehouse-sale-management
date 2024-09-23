package com.akobir.wsm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cs_customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "org_id", nullable = false)
    private Organization organization;

    @Column(name = "name", nullable = false)
    private String name;
}
