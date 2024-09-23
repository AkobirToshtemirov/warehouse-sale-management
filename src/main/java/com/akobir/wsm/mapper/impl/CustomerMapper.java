package com.akobir.wsm.mapper.impl;

import com.akobir.wsm.dto.preview.CustomerPreview;
import com.akobir.wsm.dto.request.CustomerRequest;
import com.akobir.wsm.dto.response.CustomerResponse;
import com.akobir.wsm.entity.Customer;
import com.akobir.wsm.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapper implements Mapper<Customer, CustomerRequest, CustomerResponse, CustomerPreview> {

    private final OrganizationMapper organizationMapper;

    @Override
    public Customer mapToEntity(CustomerRequest request) {
        if (request == null) return null;

        Customer customer = new Customer();
        customer.setName(request.name());
        return customer;
    }

    @Override
    public CustomerPreview mapToPreview(Customer customer) {
        if (customer == null) return null;

        return new CustomerPreview(
                customer.getId(),
                customer.getName()
        );
    }

    @Override
    public CustomerResponse mapToResponse(Customer customer) {
        if (customer == null) return null;

        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                organizationMapper.mapToPreview(customer.getOrganization()),
                customer.isDeleted(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }
}
