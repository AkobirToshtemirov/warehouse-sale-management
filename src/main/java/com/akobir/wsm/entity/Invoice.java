package com.akobir.wsm.entity;

import com.akobir.wsm.entity.enums.InvoiceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cs_invoices")
@Getter
@Setter
@NoArgsConstructor
public class Invoice extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "org_id", nullable = false)
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "num", nullable = false)
    private int num;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InvoiceStatus status;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @OneToMany(mappedBy = "invoice")
    private List<IncomingProduct> incomingProducts;

    @OneToMany(mappedBy = "invoice")
    private List<OutgoingProduct> outgoingProducts;
}

