package com.akobir.wsm.repository;

import com.akobir.wsm.entity.IncomingProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IncomingProductRepository extends JpaRepository<IncomingProduct, UUID> {
    List<IncomingProduct> findByOrganizationId(UUID orgId);

    List<IncomingProduct> findByInvoiceId(UUID invoiceId);
}

