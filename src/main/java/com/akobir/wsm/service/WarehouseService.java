package com.akobir.wsm.service;

import com.akobir.wsm.dto.preview.WarehousePreview;
import com.akobir.wsm.dto.request.WarehouseRequest;
import com.akobir.wsm.dto.response.WarehouseResponse;
import com.akobir.wsm.entity.Warehouse;

import java.util.List;
import java.util.UUID;

public interface WarehouseService extends BaseService<Warehouse, UUID, WarehouseRequest, WarehouseResponse, WarehousePreview> {
    List<WarehousePreview> getAllByOrganization(UUID orgId);

    Warehouse getWarehouseById(UUID id);
}
