package com.akobir.wsm.controller;

import com.akobir.wsm.dto.preview.CustomerPreview;
import com.akobir.wsm.dto.request.CustomerRequest;
import com.akobir.wsm.dto.response.CustomerResponse;
import com.akobir.wsm.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/organization/{orgId}")
    public ResponseEntity<List<CustomerPreview>> getCustomersByOrganization(@PathVariable UUID orgId) {
        List<CustomerPreview> customers = customerService.getAllCustomersByOrganization(orgId);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/previews")
    public ResponseEntity<List<CustomerPreview>> getAllCustomerPreviews() {
        List<CustomerPreview> customerPreviews = customerService.getAllPreviews();
        return ResponseEntity.ok(customerPreviews);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable UUID customerId) {
        CustomerResponse customerResponse = customerService.getById(customerId);
        return ResponseEntity.ok(customerResponse);
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse createdCustomer = customerService.create(customerRequest);
        return ResponseEntity.ok(createdCustomer);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable UUID customerId, @RequestBody CustomerRequest customerRequest) {
        CustomerResponse updatedCustomer = customerService.update(customerId, customerRequest);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID customerId) {
        customerService.delete(customerId);
        return ResponseEntity.noContent().build();
    }
}
