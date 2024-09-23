package com.akobir.wsm.repository;

import com.akobir.wsm.entity.OutgoingProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutgoingProductRepository extends JpaRepository<OutgoingProduct, UUID> {
    List<OutgoingProduct> findByOrganizationId(UUID orgId);

    List<OutgoingProduct> findByInvoiceId(UUID invoiceId);
}

