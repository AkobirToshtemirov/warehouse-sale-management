package com.akobir.wsm.service.impl;

import com.akobir.wsm.dto.preview.OrganizationPreview;
import com.akobir.wsm.dto.request.OrganizationRequest;
import com.akobir.wsm.dto.response.OrganizationResponse;
import com.akobir.wsm.entity.Organization;
import com.akobir.wsm.exception.CustomNotFoundException;
import com.akobir.wsm.mapper.impl.OrganizationMapper;
import com.akobir.wsm.repository.OrganizationRepository;
import com.akobir.wsm.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    @Override
    public OrganizationResponse create(OrganizationRequest organizationRequest) {
        Organization organization = organizationRepository.save(organizationMapper.mapToEntity(organizationRequest));
        return organizationMapper.mapToResponse(organization);
    }

    @Override
    public OrganizationResponse update(UUID uuid, OrganizationRequest organizationRequest) {
        getOrganizationById(uuid);
        Organization updatedOrganization = organizationMapper.mapToEntity(organizationRequest);
        updatedOrganization.setId(uuid);
        return organizationMapper.mapToResponse(organizationRepository.save(updatedOrganization));
    }


    @Override
    public OrganizationResponse getById(UUID uuid) {
        return organizationMapper.mapToResponse(getOrganizationById(uuid));
    }

    @Override
    public List<OrganizationPreview> getAllPreviews() {
        return organizationRepository.findAll()
                .stream()
                .map(organizationMapper::mapToPreview)
                .toList();
    }

    @Override
    public void delete(UUID uuid) {
        organizationRepository.delete(getOrganizationById(uuid));
    }

    @Override
    public Organization getOrganizationById(UUID id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Organization not found with id: " + id));
    }
}
