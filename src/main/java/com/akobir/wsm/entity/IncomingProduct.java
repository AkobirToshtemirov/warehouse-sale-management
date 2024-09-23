package com.akobir.wsm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cs_income_products")
@Getter
@Setter
@NoArgsConstructor
public class IncomingProduct extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "org_id", nullable = false)
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "num", nullable = false)
    private int num;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "total", nullable = false)
    private double total;
}
