package com.akobir.wsm.controller;

import com.akobir.wsm.dto.preview.IncomingProductPreview;
import com.akobir.wsm.dto.request.IncomingProductRequest;
import com.akobir.wsm.dto.response.IncomingProductResponse;
import com.akobir.wsm.service.IncomingProductService;
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

    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<List<IncomingProductPreview>> getIncomingProductsByInvoiceId(@PathVariable UUID invoiceId) {
        List<IncomingProductPreview> incomingProducts = incomingProductService.getIncomingProductsByInvoiceId(invoiceId);
        return ResponseEntity.ok(incomingProducts);
    }

    @GetMapping("/organization/{orgId}")
    public ResponseEntity<List<IncomingProductPreview>> getAllIncomingProductsByOrganization(@PathVariable UUID orgId) {
        List<IncomingProductPreview> incomingProductPreviews = incomingProductService.getAllByOrganization(orgId);
        return ResponseEntity.ok(incomingProductPreviews);
    }

    @GetMapping("/previews")
    public ResponseEntity<List<IncomingProductPreview>> getAllIncomingProductPreviews() {
        List<IncomingProductPreview> incomingProductPreviews = incomingProductService.getAllPreviews();
        return ResponseEntity.ok(incomingProductPreviews);
    }

    @GetMapping("/{incomingProductId}")
    public ResponseEntity<IncomingProductResponse> getIncomingProductById(@PathVariable UUID incomingProductId) {
        IncomingProductResponse incomingProductResponse = incomingProductService.getById(incomingProductId);
        return ResponseEntity.ok(incomingProductResponse);
    }

    @PostMapping
    public ResponseEntity<IncomingProductResponse> createIncomingProduct(@RequestBody IncomingProductRequest incomingProductRequest) {
        IncomingProductResponse createdIncomingProduct = incomingProductService.create(incomingProductRequest);
        return ResponseEntity.ok(createdIncomingProduct);
    }

    @PutMapping("/{incomingProductId}")
    public ResponseEntity<IncomingProductResponse> updateIncomingProduct(@PathVariable UUID incomingProductId, @RequestBody IncomingProductRequest incomingProductRequest) {
        IncomingProductResponse updatedIncomingProduct = incomingProductService.update(incomingProductId, incomingProductRequest);
        return ResponseEntity.ok(updatedIncomingProduct);
    }

    @DeleteMapping("/{incomingProductId}")
    public ResponseEntity<Void> deleteIncomingProduct(@PathVariable UUID incomingProductId) {
        incomingProductService.delete(incomingProductId);
        return ResponseEntity.noContent().build();
    }
}
