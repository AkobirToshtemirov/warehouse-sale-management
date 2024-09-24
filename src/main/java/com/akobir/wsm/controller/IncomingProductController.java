package com.akobir.wsm.controller;

import com.akobir.wsm.dto.preview.IncomingProductPreview;
import com.akobir.wsm.dto.request.IncomingProductRequest;
import com.akobir.wsm.dto.response.IncomingProductResponse;
import com.akobir.wsm.service.IncomingProductService;
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
@RequestMapping("/api/v1/incoming-products")
@RequiredArgsConstructor
public class IncomingProductController {

    private final IncomingProductService incomingProductService;

    @Operation(summary = "Get incoming products by invoice ID",
            description = "Retrieves a list of incoming products associated with the specified invoice ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incoming products retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Invoice not found")
    })
    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<List<IncomingProductPreview>> getIncomingProductsByInvoiceId(
            @Parameter(description = "ID of the invoice", required = true)
            @PathVariable UUID invoiceId) {
        List<IncomingProductPreview> incomingProducts = incomingProductService.getIncomingProductsByInvoiceId(invoiceId);
        return ResponseEntity.ok(incomingProducts);
    }

    @Operation(summary = "Get all incoming products by organization",
            description = "Retrieves a list of incoming products associated with the specified organization ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incoming products retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @GetMapping("/organization/{orgId}")
    public ResponseEntity<List<IncomingProductPreview>> getAllIncomingProductsByOrganization(
            @Parameter(description = "ID of the organization", required = true)
            @PathVariable UUID orgId) {
        List<IncomingProductPreview> incomingProductPreviews = incomingProductService.getAllByOrganization(orgId);
        return ResponseEntity.ok(incomingProductPreviews);
    }

    @Operation(summary = "Get all incoming product previews",
            description = "Retrieves a list of previews for all incoming products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incoming product previews retrieved successfully")
    })
    @GetMapping("/previews")
    public ResponseEntity<List<IncomingProductPreview>> getAllIncomingProductPreviews() {
        List<IncomingProductPreview> incomingProductPreviews = incomingProductService.getAllPreviews();
        return ResponseEntity.ok(incomingProductPreviews);
    }

    @Operation(summary = "Get an incoming product by ID",
            description = "Retrieves detailed information about an incoming product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incoming product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Incoming product not found")
    })
    @GetMapping("/{incomingProductId}")
    public ResponseEntity<IncomingProductResponse> getIncomingProductById(
            @Parameter(description = "ID of the incoming product to retrieve", required = true)
            @PathVariable UUID incomingProductId) {
        IncomingProductResponse incomingProductResponse = incomingProductService.getById(incomingProductId);
        return ResponseEntity.ok(incomingProductResponse);
    }

    @Operation(summary = "Create a new incoming product",
            description = "Creates a new incoming product with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Incoming product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid incoming product details provided")
    })
    @PostMapping
    public ResponseEntity<IncomingProductResponse> createIncomingProduct(
            @Parameter(description = "Incoming product details to create", required = true)
            @RequestBody IncomingProductRequest incomingProductRequest) {
        IncomingProductResponse createdIncomingProduct = incomingProductService.create(incomingProductRequest);
        return ResponseEntity.ok(createdIncomingProduct);
    }

    @Operation(summary = "Update an incoming product",
            description = "Updates the details of an existing incoming product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incoming product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Incoming product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid incoming product details provided")
    })
    @PutMapping("/{incomingProductId}")
    public ResponseEntity<IncomingProductResponse> updateIncomingProduct(
            @Parameter(description = "ID of the incoming product to update", required = true)
            @PathVariable UUID incomingProductId,
            @Parameter(description = "Updated incoming product details", required = true)
            @RequestBody IncomingProductRequest incomingProductRequest) {
        IncomingProductResponse updatedIncomingProduct = incomingProductService.update(incomingProductId, incomingProductRequest);
        return ResponseEntity.ok(updatedIncomingProduct);
    }

    @Operation(summary = "Delete an incoming product",
            description = "Deletes the incoming product associated with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Incoming product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Incoming product not found")
    })
    @DeleteMapping("/{incomingProductId}")
    public ResponseEntity<Void> deleteIncomingProduct(
            @Parameter(description = "ID of the incoming product to delete", required = true)
            @PathVariable UUID incomingProductId) {
        incomingProductService.delete(incomingProductId);
        return ResponseEntity.noContent().build();
    }
}
