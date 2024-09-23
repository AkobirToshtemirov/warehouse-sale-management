package com.akobir.wsm.service;

import com.akobir.wsm.dto.preview.CustomerPreview;
import com.akobir.wsm.dto.request.CustomerRequest;
import com.akobir.wsm.dto.response.CustomerResponse;
import com.akobir.wsm.entity.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService extends BaseService<Customer, UUID, CustomerRequest, CustomerResponse, CustomerPreview> {
    List<CustomerPreview> getAllCustomersByOrganization(UUID orgId);

    Customer getCustomerById(UUID id);
}
