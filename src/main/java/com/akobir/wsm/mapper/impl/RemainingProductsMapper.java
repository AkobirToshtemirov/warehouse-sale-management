package com.akobir.wsm.mapper.impl;

import com.akobir.wsm.dto.response.RemainingProductByDateResponse;
import com.akobir.wsm.dto.response.RemainingProductByOrgResponse;
import com.akobir.wsm.dto.response.RemainingProductByWarehouseResponse;
import com.akobir.wsm.entity.Organization;
import com.akobir.wsm.entity.Warehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RemainingProductsMapper {

    private final OrganizationMapper organizationMapper;
    private final WarehouseMapper warehouseMapper;

    public RemainingProductByOrgResponse toOrgResponse(Organization organization, Integer quantity) {
        return new RemainingProductByOrgResponse(organizationMapper.mapToPreview(organization), quantity);
    }

    public RemainingProductByWarehouseResponse toWarehouseResponse(Warehouse warehouse, Integer quantity) {
        return new RemainingProductByWarehouseResponse(warehouseMapper.mapToPreview(warehouse), quantity);
    }

    public RemainingProductByDateResponse toDateResponse(LocalDateTime startDate, LocalDateTime endDate, Integer quantity) {
        return new RemainingProductByDateResponse(startDate, endDate, quantity);
    }


}
