package com.akobir.wsm.repository;

import com.akobir.wsm.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {
    List<Warehouse> findByOrganizationId(UUID orgId);
}
