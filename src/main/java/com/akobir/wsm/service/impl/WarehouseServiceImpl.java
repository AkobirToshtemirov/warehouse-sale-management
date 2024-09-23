package com.akobir.wsm.service.impl;

import com.akobir.wsm.dto.preview.WarehousePreview;
import com.akobir.wsm.dto.request.WarehouseRequest;
import com.akobir.wsm.dto.response.WarehouseResponse;
import com.akobir.wsm.entity.Organization;
import com.akobir.wsm.entity.Warehouse;
import com.akobir.wsm.exception.CustomNotFoundException;
import com.akobir.wsm.mapper.impl.WarehouseMapper;
import com.akobir.wsm.repository.WarehouseRepository;
import com.akobir.wsm.service.OrganizationService;
import com.akobir.wsm.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;
    private final OrganizationService organizationService;

    @Override
    public List<WarehousePreview> getAllByOrganization(UUID orgId) {
        return warehouseRepository.findByOrganizationId(orgId)
                .stream()
                .map(warehouseMapper::mapToPreview)
                .toList();
    }

    @Override
    public Warehouse getWarehouseById(UUID id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Warehouse not found with id: " + id));
    }

    @Override
    public WarehouseResponse create(WarehouseRequest warehouseRequest) {
        Organization organization = organizationService.getOrganizationById(warehouseRequest.orgId());
        Warehouse warehouse = warehouseMapper.mapToEntity(warehouseRequest);
        warehouse.setOrganization(organization);

        return warehouseMapper.mapToResponse(warehouseRepository.save(warehouse));
    }

    @Override
    public WarehouseResponse update(UUID uuid, WarehouseRequest warehouseRequest) {
        getWarehouseById(uuid);
        Organization organization = organizationService.getOrganizationById(warehouseRequest.orgId());
        Warehouse updatedWarehouse = warehouseMapper.mapToEntity(warehouseRequest);
        updatedWarehouse.setId(uuid);
        updatedWarehouse.setOrganization(organization);
        return warehouseMapper.mapToResponse(warehouseRepository.save(updatedWarehouse));
    }

    @Override
    public WarehouseResponse getById(UUID uuid) {
        return warehouseMapper.mapToResponse(getWarehouseById(uuid));
    }

    @Override
    public List<WarehousePreview> getAllPreviews() {
        return warehouseRepository.findAll()
                .stream()
                .map(warehouseMapper::mapToPreview)
                .toList();
    }

    @Override
    public void delete(UUID uuid) {
        warehouseRepository.delete(getWarehouseById(uuid));
    }
}
