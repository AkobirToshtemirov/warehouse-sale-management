package com.akobir.wsm.controller;

import com.akobir.wsm.dto.preview.OrganizationPreview;
import com.akobir.wsm.dto.request.OrganizationRequest;
import com.akobir.wsm.dto.response.OrganizationResponse;
import com.akobir.wsm.service.OrganizationService;
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
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @Operation(summary = "Create a new organization",
            description = "Creates a new organization with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Organization created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid organization details provided")
    })
    @PostMapping
    public ResponseEntity<OrganizationResponse> createOrganization(
            @Parameter(description = "Organization details to create", required = true)
            @RequestBody OrganizationRequest organizationRequest) {
        OrganizationResponse organizationResponse = organizationService.create(organizationRequest);
        return ResponseEntity.ok(organizationResponse);
    }

    @Operation(summary = "Update an organization",
            description = "Updates the details of an existing organization by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Organization updated successfully"),
            @ApiResponse(responseCode = "404", description = "Organization not found"),
            @ApiResponse(responseCode = "400", description = "Invalid organization details provided")
    })
    @PutMapping("/{organizationId}")
    public ResponseEntity<OrganizationResponse> updateOrganization(
            @Parameter(description = "ID of the organization to update", required = true)
            @PathVariable UUID organizationId,
            @Parameter(description = "Updated organization details", required = true)
            @RequestBody OrganizationRequest organizationRequest) {
        OrganizationResponse updatedOrganization = organizationService.update(organizationId, organizationRequest);
        return ResponseEntity.ok(updatedOrganization);
    }

    @Operation(summary = "Get an organization by ID",
            description = "Retrieves detailed information about an organization by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Organization retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @GetMapping("/{organizationId}")
    public ResponseEntity<OrganizationResponse> getOrganizationById(
            @Parameter(description = "ID of the organization to retrieve", required = true)
            @PathVariable UUID organizationId) {
        OrganizationResponse organizationResponse = organizationService.getById(organizationId);
        return ResponseEntity.ok(organizationResponse);
    }

    @Operation(summary = "Get all organization previews",
            description = "Retrieves a list of previews for all organizations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Organization previews retrieved successfully")
    })
    @GetMapping("/previews")
    public ResponseEntity<List<OrganizationPreview>> getAllOrganizationPreviews() {
        List<OrganizationPreview> organizationPreviews = organizationService.getAllPreviews();
        return ResponseEntity.ok(organizationPreviews);
    }

    @Operation(summary = "Delete an organization",
            description = "Deletes the organization associated with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Organization deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @DeleteMapping("/{organizationId}")
    public ResponseEntity<Void> deleteOrganization(
            @Parameter(description = "ID of the organization to delete", required = true)
            @PathVariable UUID organizationId) {
        organizationService.delete(organizationId);
        return ResponseEntity.noContent().build();
    }
}
