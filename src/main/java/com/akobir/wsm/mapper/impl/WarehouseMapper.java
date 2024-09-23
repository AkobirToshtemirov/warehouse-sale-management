package com.akobir.wsm.mapper.impl;

import com.akobir.wsm.dto.preview.WarehousePreview;
import com.akobir.wsm.dto.request.WarehouseRequest;
import com.akobir.wsm.dto.response.WarehouseResponse;
import com.akobir.wsm.entity.Organization;
import com.akobir.wsm.entity.Warehouse;
import com.akobir.wsm.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WarehouseMapper implements Mapper<Warehouse, WarehouseRequest, WarehouseResponse, WarehousePreview> {

    private final OrganizationMapper organizationMapper;

    @Override
    public Warehouse mapToEntity(WarehouseRequest warehouseRequest) {
        if (warehouseRequest == null) return null;

        Warehouse warehouse = new Warehouse();
        warehouse.setName(warehouseRequest.name());
        return warehouse;
    }

    @Override
    public WarehousePreview mapToPreview(Warehouse warehouse) {
        if (warehouse == null) return null;

        return new WarehousePreview(
                warehouse.getId(),
                warehouse.getName()
        );
    }

    @Override
    public WarehouseResponse mapToResponse(Warehouse warehouse) {
        if (warehouse == null) return null;
        Organization organization = warehouse.getOrganization();

        return new WarehouseResponse(
                warehouse.getId(),
                warehouse.getName(),
                organizationMapper.mapToPreview(organization),
                warehouse.isDeleted(),
                warehouse.getCreatedAt(),
                warehouse.getUpdatedAt()
        );
    }
}
