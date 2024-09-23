package com.akobir.wsm.service.impl;

import com.akobir.wsm.dto.preview.CustomerPreview;
import com.akobir.wsm.dto.request.CustomerRequest;
import com.akobir.wsm.dto.response.CustomerResponse;
import com.akobir.wsm.entity.Customer;
import com.akobir.wsm.entity.Organization;
import com.akobir.wsm.exception.CustomNotFoundException;
import com.akobir.wsm.mapper.impl.CustomerMapper;
import com.akobir.wsm.repository.CustomerRepository;
import com.akobir.wsm.service.CustomerService;
import com.akobir.wsm.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final OrganizationService organizationService;

    @Override
    public List<CustomerPreview> getAllCustomersByOrganization(UUID orgId) {
        return customerRepository.findByOrganizationId(orgId)
                .stream()
                .map(customerMapper::mapToPreview)
                .toList();
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Customer not found with id: " + id));
    }

    @Override
    public CustomerResponse create(CustomerRequest customerRequest) {
        Organization organization = organizationService.getOrganizationById(customerRequest.orgId());
        Customer customer = customerMapper.mapToEntity(customerRequest);
        customer.setOrganization(organization);
        return customerMapper.mapToResponse(customerRepository.save(customer));
    }

    @Override
    public CustomerResponse update(UUID uuid, CustomerRequest customerRequest) {
        getCustomerById(uuid);
        Organization organization = organizationService.getOrganizationById(customerRequest.orgId());
        Customer updatedCustomer = customerMapper.mapToEntity(customerRequest);
        updatedCustomer.setId(uuid);
        updatedCustomer.setOrganization(organization);
        return customerMapper.mapToResponse(customerRepository.save(updatedCustomer));
    }

    @Override
    public CustomerResponse getById(UUID uuid) {
        return customerMapper.mapToResponse(getCustomerById(uuid));
    }

    @Override
    public List<CustomerPreview> getAllPreviews() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::mapToPreview)
                .toList();
    }

    @Override
    public void delete(UUID uuid) {
        customerRepository.delete(getCustomerById(uuid));
    }
}
