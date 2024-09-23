package com.akobir.wsm.repository;

import com.akobir.wsm.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    List<Invoice> findByOrganizationId(UUID orgId);

    List<Invoice> findByOrganizationIdAndWarehouseId(UUID orgId, UUID warehouseId);
}
