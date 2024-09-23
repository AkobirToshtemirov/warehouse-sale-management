package com.akobir.wsm.repository;

import com.akobir.wsm.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT SUM(ip.num) - COALESCE(SUM(op.num), 0) FROM IncomingProduct ip " +
            "LEFT JOIN OutgoingProduct op ON ip.product.id = op.product.id " +
            "WHERE ip.organization.id = :orgId")
    Integer getRemainingProductsByOrg(@Param("orgId") UUID orgId);

    @Query("SELECT SUM(ip.num) - COALESCE(SUM(op.num), 0) FROM IncomingProduct ip " +
            "LEFT JOIN OutgoingProduct op ON ip.product.id = op.product.id " +
            "WHERE ip.invoice.warehouse.id = :warehouseId")
    Integer getRemainingProductsByWarehouse(@Param("warehouseId") UUID warehouseId);

    @Query("SELECT SUM(ip.num) - COALESCE(SUM(op.num), 0) FROM IncomingProduct ip " +
            "LEFT JOIN OutgoingProduct op ON ip.product.id = op.product.id " +
            "WHERE ip.organization.id = :orgId AND ip.createdAt BETWEEN :startDate AND :endDate")
    Integer getRemainingProductsByOrganizationBetweenDates(@Param("orgId") UUID orgId,
                                                           @Param("startDate") LocalDate startDate,
                                                           @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(ip.num) - COALESCE(SUM(op.num), 0) FROM IncomingProduct ip " +
            "LEFT JOIN OutgoingProduct op ON ip.product.id = op.product.id " +
            "WHERE ip.invoice.warehouse.id = :warehouseId AND ip.createdAt BETWEEN :startDate AND :endDate")
    Integer getRemainingProductsByWarehouseBetweenDates(@Param("warehouseId") UUID warehouseId,
                                                        @Param("startDate") LocalDate startDate,
                                                        @Param("endDate") LocalDate endDate);

}
