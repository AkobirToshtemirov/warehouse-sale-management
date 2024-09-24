package com.akobir.wsm.controller;

import com.akobir.wsm.dto.preview.OutgoingProductPreview;
import com.akobir.wsm.dto.request.OutgoingProductRequest;
import com.akobir.wsm.dto.response.OutgoingProductResponse;
import com.akobir.wsm.service.OutgoingProductService;
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
@RequestMapping("/api/v1/outgoing-products")
@RequiredArgsConstructor
public class OutgoingProductController {

    private final OutgoingProductService outgoingProductService;

    @Operation(summary = "Create a new outgoing product",
            description = "Creates a new outgoing product with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Outgoing product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid outgoing product details provided")
    })
    @PostMapping
    public ResponseEntity<OutgoingProductResponse> createOutgoingProduct(
            @Parameter(description = "Outgoing product details to create", required = true)
            @RequestBody OutgoingProductRequest outgoingProductRequest) {
        OutgoingProductResponse response = outgoingProductService.create(outgoingProductRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an outgoing product",
            description = "Updates the details of an existing outgoing product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Outgoing product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Outgoing product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid outgoing product details provided")
    })
    @PutMapping("/{outgoingProductId}")
    public ResponseEntity<OutgoingProductResponse> updateOutgoingProduct(
            @Parameter(description = "ID of the outgoing product to update", required = true)
            @PathVariable UUID outgoingProductId,
            @Parameter(description = "Updated outgoing product details", required = true)
            @RequestBody OutgoingProductRequest outgoingProductRequest) {
        OutgoingProductResponse response = outgoingProductService.update(outgoingProductId, outgoingProductRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get an outgoing product by ID",
            description = "Retrieves detailed information about an outgoing product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Outgoing product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Outgoing product not found")
    })
    @GetMapping("/{outgoingProductId}")
    public ResponseEntity<OutgoingProductResponse> getOutgoingProductById(
            @Parameter(description = "ID of the outgoing product to retrieve", required = true)
            @PathVariable UUID outgoingProductId) {
        OutgoingProductResponse response = outgoingProductService.getById(outgoingProductId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get outgoing products by invoice ID",
            description = "Retrieves a list of outgoing products associated with a specific invoice ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Outgoing products retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Invoice not found")
    })
    @GetMapping("/by-invoice/{invoiceId}")
    public ResponseEntity<List<OutgoingProductPreview>> getOutgoingProductsByInvoiceId(
            @Parameter(description = "ID of the invoice to retrieve outgoing products", required = true)
            @PathVariable UUID invoiceId) {
        List<OutgoingProductPreview> previews = outgoingProductService.getOutgoingProductsByInvoiceId(invoiceId);
        return ResponseEntity.ok(previews);
    }

    @Operation(summary = "Get all outgoing products by organization ID",
            description = "Retrieves a list of outgoing products associated with a specific organization ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Outgoing products retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @GetMapping("/by-organization/{orgId}")
    public ResponseEntity<List<OutgoingProductPreview>> getAllByOrganization(
            @Parameter(description = "ID of the organization to retrieve outgoing products", required = true)
            @PathVariable UUID orgId) {
        List<OutgoingProductPreview> previews = outgoingProductService.getAllByOrganization(orgId);
        return ResponseEntity.ok(previews);
    }

    @Operation(summary = "Get all outgoing product previews",
            description = "Retrieves a list of previews for all outgoing products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Outgoing product previews retrieved successfully")
    })
    @GetMapping("/previews")
    public ResponseEntity<List<OutgoingProductPreview>> getAllOutgoingProductPreviews() {
        List<OutgoingProductPreview> previews = outgoingProductService.getAllPreviews();
        return ResponseEntity.ok(previews);
    }

    @Operation(summary = "Delete an outgoing product",
            description = "Deletes the outgoing product associated with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Outgoing product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Outgoing product not found")
    })
    @DeleteMapping("/{outgoingProductId}")
    public ResponseEntity<Void> deleteOutgoingProduct(
            @Parameter(description = "ID of the outgoing product to delete", required = true)
            @PathVariable UUID outgoingProductId) {
        outgoingProductService.delete(outgoingProductId);
        return ResponseEntity.noContent().build();
    }
}
