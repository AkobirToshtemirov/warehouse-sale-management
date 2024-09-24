package com.akobir.wsm.controller;

import com.akobir.wsm.dto.preview.OrganizationPreview;
import com.akobir.wsm.dto.request.OrganizationRequest;
import com.akobir.wsm.dto.response.OrganizationResponse;
import com.akobir.wsm.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<OrganizationResponse> createOrganization(@RequestBody OrganizationRequest organizationRequest) {
        OrganizationResponse organizationResponse = organizationService.create(organizationRequest);
        return ResponseEntity.ok(organizationResponse);
    }

    @PutMapping("/{organizationId}")
    public ResponseEntity<OrganizationResponse> updateOrganization(@PathVariable UUID organizationId, @RequestBody OrganizationRequest organizationRequest) {
        OrganizationResponse updatedOrganization = organizationService.update(organizationId, organizationRequest);
        return ResponseEntity.ok(updatedOrganization);
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<OrganizationResponse> getOrganizationById(@PathVariable UUID organizationId) {
        OrganizationResponse organizationResponse = organizationService.getById(organizationId);
        return ResponseEntity.ok(organizationResponse);
    }

    @GetMapping("/previews")
    public ResponseEntity<List<OrganizationPreview>> getAllOrganizationPreviews() {
        List<OrganizationPreview> organizationPreviews = organizationService.getAllPreviews();
        return ResponseEntity.ok(organizationPreviews);
    }

    @DeleteMapping("/{organizationId}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable UUID organizationId) {
        organizationService.delete(organizationId);
        return ResponseEntity.noContent().build();
    }
}
