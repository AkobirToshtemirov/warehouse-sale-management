package com.akobir.wsm.dto.response;

import com.akobir.wsm.dto.preview.OrganizationPreview;

public record RemainingProductByOrgResponse(
        OrganizationPreview organization,
        Integer quantity
) {
}
