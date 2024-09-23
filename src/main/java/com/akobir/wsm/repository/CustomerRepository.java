package com.akobir.wsm.repository;

import com.akobir.wsm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findByOrganizationId(UUID orgId);
}
