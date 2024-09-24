package com.akobir.wsm.controller;

import com.akobir.wsm.dto.preview.InvoicePreview;
import com.akobir.wsm.dto.request.InvoiceRequest;
import com.akobir.wsm.dto.response.InvoiceResponse;
import com.akobir.wsm.service.InvoiceService;
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

    @GetMapping("/organization/{orgId}")
    public ResponseEntity<List<InvoicePreview>> getInvoicesByOrganizationId(@PathVariable UUID orgId) {
        List<InvoicePreview> invoices = invoiceService.getByOrganizationId(orgId);
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/organization/{orgId}/warehouse/{warehouseId}")
    public ResponseEntity<List<InvoicePreview>> getInvoicesByOrganizationAndWarehouse(@PathVariable UUID orgId, @PathVariable UUID warehouseId) {
        List<InvoicePreview> invoices = invoiceService.getByOrganizationIdAndWarehouseId(orgId, warehouseId);
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResponse> getInvoiceById(@PathVariable UUID invoiceId) {
        InvoiceResponse invoiceResponse = invoiceService.getById(invoiceId);
        return ResponseEntity.ok(invoiceResponse);
    }

    @GetMapping("/previews")
    public ResponseEntity<List<InvoicePreview>> getAllInvoicePreviews() {
        List<InvoicePreview> invoicePreviews = invoiceService.getAllPreviews();
        return ResponseEntity.ok(invoicePreviews);
    }

    @PostMapping
    public ResponseEntity<InvoiceResponse> createInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        InvoiceResponse createdInvoice = invoiceService.create(invoiceRequest);
        return ResponseEntity.ok(createdInvoice);
    }

    @PutMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResponse> updateInvoice(@PathVariable UUID invoiceId, @RequestBody InvoiceRequest invoiceRequest) {
        InvoiceResponse updatedInvoice = invoiceService.update(invoiceId, invoiceRequest);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable UUID invoiceId) {
        invoiceService.delete(invoiceId);
        return ResponseEntity.noContent().build();
    }
}
