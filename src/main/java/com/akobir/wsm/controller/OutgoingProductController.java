package com.akobir.wsm.controller;

import com.akobir.wsm.dto.preview.OutgoingProductPreview;
import com.akobir.wsm.dto.request.OutgoingProductRequest;
import com.akobir.wsm.dto.response.OutgoingProductResponse;
import com.akobir.wsm.service.OutgoingProductService;
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

    @PostMapping
    public ResponseEntity<OutgoingProductResponse> createOutgoingProduct(@RequestBody OutgoingProductRequest outgoingProductRequest) {
        OutgoingProductResponse response = outgoingProductService.create(outgoingProductRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{outgoingProductId}")
    public ResponseEntity<OutgoingProductResponse> updateOutgoingProduct(@PathVariable UUID outgoingProductId,
                                                                         @RequestBody OutgoingProductRequest outgoingProductRequest) {
        OutgoingProductResponse response = outgoingProductService.update(outgoingProductId, outgoingProductRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{outgoingProductId}")
    public ResponseEntity<OutgoingProductResponse> getOutgoingProductById(@PathVariable UUID outgoingProductId) {
        OutgoingProductResponse response = outgoingProductService.getById(outgoingProductId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-invoice/{invoiceId}")
    public ResponseEntity<List<OutgoingProductPreview>> getOutgoingProductsByInvoiceId(@PathVariable UUID invoiceId) {
        List<OutgoingProductPreview> previews = outgoingProductService.getOutgoingProductsByInvoiceId(invoiceId);
        return ResponseEntity.ok(previews);
    }

    @GetMapping("/by-organization/{orgId}")
    public ResponseEntity<List<OutgoingProductPreview>> getAllByOrganization(@PathVariable UUID orgId) {
        List<OutgoingProductPreview> previews = outgoingProductService.getAllByOrganization(orgId);
        return ResponseEntity.ok(previews);
    }

    @GetMapping("/previews")
    public ResponseEntity<List<OutgoingProductPreview>> getAllOutgoingProductPreviews() {
        List<OutgoingProductPreview> previews = outgoingProductService.getAllPreviews();
        return ResponseEntity.ok(previews);
    }

    @DeleteMapping("/{outgoingProductId}")
    public ResponseEntity<Void> deleteOutgoingProduct(@PathVariable UUID outgoingProductId) {
        outgoingProductService.delete(outgoingProductId);
        return ResponseEntity.noContent().build();
    }
}
