package com.akobir.wsm.controller;

import com.akobir.wsm.dto.preview.CustomerPreview;
import com.akobir.wsm.dto.request.CustomerRequest;
import com.akobir.wsm.dto.response.CustomerResponse;
import com.akobir.wsm.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all customers by organization",
            description = "Retrieves a list of customers associated with the specified organization ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @GetMapping("/organization/{orgId}")
    public ResponseEntity<List<CustomerPreview>> getCustomersByOrganization(
            @Parameter(description = "ID of the organization", required = true)
            @PathVariable UUID orgId) {
        List<CustomerPreview> customers = customerService.getAllCustomersByOrganization(orgId);
        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "Get all customer previews",
            description = "Retrieves a list of previews for all customers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer previews retrieved successfully")
    })
    @GetMapping("/previews")
    public ResponseEntity<List<CustomerPreview>> getAllCustomerPreviews() {
        List<CustomerPreview> customerPreviews = customerService.getAllPreviews();
        return ResponseEntity.ok(customerPreviews);
    }

    @Operation(summary = "Get a customer by ID",
            description = "Retrieves detailed information about a customer by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(
            @Parameter(description = "ID of the customer to retrieve", required = true)
            @PathVariable UUID customerId) {
        CustomerResponse customerResponse = customerService.getById(customerId);
        return ResponseEntity.ok(customerResponse);
    }

    @Operation(summary = "Create a new customer",
            description = "Creates a new customer with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid customer details provided")
    })
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @Parameter(description = "Customer details to create", required = true)
            @RequestBody CustomerRequest customerRequest) {
        CustomerResponse createdCustomer = customerService.create(customerRequest);
        return ResponseEntity.ok(createdCustomer);
    }

    @Operation(summary = "Update a customer",
            description = "Updates the details of an existing customer by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "400", description = "Invalid customer details provided")
    })
    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @Parameter(description = "ID of the customer to update", required = true)
            @PathVariable UUID customerId,
            @Parameter(description = "Updated customer details", required = true)
            @RequestBody CustomerRequest customerRequest) {
        CustomerResponse updatedCustomer = customerService.update(customerId, customerRequest);
        return ResponseEntity.ok(updatedCustomer);
    }

    @Operation(summary = "Delete a customer",
            description = "Deletes the customer associated with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "ID of the customer to delete", required = true)
            @PathVariable UUID customerId) {
        customerService.delete(customerId);
        return ResponseEntity.noContent().build();
    }
}
