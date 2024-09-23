package com.akobir.wsm.service;

import com.akobir.wsm.dto.preview.OrganizationPreview;
import com.akobir.wsm.dto.request.OrganizationRequest;
import com.akobir.wsm.dto.response.OrganizationResponse;
import com.akobir.wsm.entity.Organization;

import java.util.UUID;

public interface OrganizationService extends BaseService<Organization, UUID, OrganizationRequest, OrganizationResponse, OrganizationPreview> {

    Organization getOrganizationById(UUID id);

}

