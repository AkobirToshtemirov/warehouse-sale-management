package com.akobir.wsm.controller;

import com.akobir.wsm.dto.preview.InvoicePreview;
import com.akobir.wsm.dto.request.InvoiceRequest;
import com.akobir.wsm.dto.response.InvoiceResponse;
import com.akobir.wsm.service.InvoiceService;
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
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Operation(summary = "Get invoices by organization ID",
            description = "Retrieves a list of invoices associated with the specified organization ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoices retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @GetMapping("/organization/{orgId}")
    public ResponseEntity<List<InvoicePreview>> getInvoicesByOrganizationId(
            @Parameter(description = "ID of the organization", required = true)
            @PathVariable UUID orgId) {
        List<InvoicePreview> invoices = invoiceService.getByOrganizationId(orgId);
        return ResponseEntity.ok(invoices);
    }

    @Operation(summary = "Get invoices by organization and warehouse",
            description = "Retrieves a list of invoices associated with the specified organization ID and warehouse ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoices retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Organization or warehouse not found")
    })
    @GetMapping("/organization/{orgId}/warehouse/{warehouseId}")
    public ResponseEntity<List<InvoicePreview>> getInvoicesByOrganizationAndWarehouse(
            @Parameter(description = "ID of the organization", required = true)
            @PathVariable UUID orgId,
            @Parameter(description = "ID of the warehouse", required = true)
            @PathVariable UUID warehouseId) {
        List<InvoicePreview> invoices = invoiceService.getByOrganizationIdAndWarehouseId(orgId, warehouseId);
        return ResponseEntity.ok(invoices);
    }

    @Operation(summary = "Get an invoice by ID",
            description = "Retrieves detailed information about an invoice by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Invoice not found")
    })
    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResponse> getInvoiceById(
            @Parameter(description = "ID of the invoice to retrieve", required = true)
            @PathVariable UUID invoiceId) {
        InvoiceResponse invoiceResponse = invoiceService.getById(invoiceId);
        return ResponseEntity.ok(invoiceResponse);
    }

    @Operation(summary = "Get all invoice previews",
            description = "Retrieves a list of previews for all invoices.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice previews retrieved successfully")
    })
    @GetMapping("/previews")
    public ResponseEntity<List<InvoicePreview>> getAllInvoicePreviews() {
        List<InvoicePreview> invoicePreviews = invoiceService.getAllPreviews();
        return ResponseEntity.ok(invoicePreviews);
    }

    @Operation(summary = "Create a new invoice",
            description = "Creates a new invoice with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Invoice created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid invoice details provided")
    })
    @PostMapping
    public ResponseEntity<InvoiceResponse> createInvoice(
            @Parameter(description = "Invoice details to create", required = true)
            @RequestBody InvoiceRequest invoiceRequest) {
        InvoiceResponse createdInvoice = invoiceService.create(invoiceRequest);
        return ResponseEntity.ok(createdInvoice);
    }

    @Operation(summary = "Update an invoice",
            description = "Updates the details of an existing invoice by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice updated successfully"),
            @ApiResponse(responseCode = "404", description = "Invoice not found"),
            @ApiResponse(responseCode = "400", description = "Invalid invoice details provided")
    })
    @PutMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResponse> updateInvoice(
            @Parameter(description = "ID of the invoice to update", required = true)
            @PathVariable UUID invoiceId,
            @Parameter(description = "Updated invoice details", required = true)
            @RequestBody InvoiceRequest invoiceRequest) {
        InvoiceResponse updatedInvoice = invoiceService.update(invoiceId, invoiceRequest);
        return ResponseEntity.ok(updatedInvoice);
    }

    @Operation(summary = "Delete an invoice",
            description = "Deletes the invoice associated with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Invoice deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Invoice not found")
    })
    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<Void> deleteInvoice(
            @Parameter(description = "ID of the invoice to delete", required = true)
            @PathVariable UUID invoiceId) {
        invoiceService.delete(invoiceId);
        return ResponseEntity.noContent().build();
    }
}
