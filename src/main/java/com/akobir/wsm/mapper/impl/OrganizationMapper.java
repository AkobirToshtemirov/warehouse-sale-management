package com.akobir.wsm.mapper.impl;

import com.akobir.wsm.dto.preview.OrganizationPreview;
import com.akobir.wsm.dto.request.OrganizationRequest;
import com.akobir.wsm.dto.response.OrganizationResponse;
import com.akobir.wsm.entity.Organization;
import com.akobir.wsm.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper implements Mapper<Organization, OrganizationRequest, OrganizationResponse, OrganizationPreview> {
    @Override
    public Organization mapToEntity(OrganizationRequest organizationRequest) {
        if (organizationRequest == null) return null;

        Organization organization = new Organization();
        organization.setName(organizationRequest.name());
        organization.setTin(organizationRequest.tin());
        return organization;
    }

    @Override
    public OrganizationPreview mapToPreview(Organization organization) {
        if (organization == null) return null;

        return new OrganizationPreview(
                organization.getId(),
                organization.getName()
        );
    }

    @Override
    public OrganizationResponse mapToResponse(Organization organization) {
        if (organization == null) return null;

        return new OrganizationResponse(
                organization.getId(),
                organization.getName(),
                organization.getTin(),
                organization.isDeleted(),
                organization.getCreatedAt(),
                organization.getUpdatedAt()
        );
    }
}
