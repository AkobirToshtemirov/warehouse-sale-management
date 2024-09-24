package com.akobir.wsm.service.impl;

import com.akobir.wsm.dto.preview.InvoicePreview;
import com.akobir.wsm.dto.request.InvoiceRequest;
import com.akobir.wsm.dto.response.InvoiceResponse;
import com.akobir.wsm.entity.Customer;
import com.akobir.wsm.entity.Invoice;
import com.akobir.wsm.entity.Organization;
import com.akobir.wsm.entity.Warehouse;
import com.akobir.wsm.exception.CustomNotFoundException;
import com.akobir.wsm.mapper.impl.InvoiceMapper;
import com.akobir.wsm.repository.InvoiceRepository;
import com.akobir.wsm.service.CustomerService;
import com.akobir.wsm.service.InvoiceService;
import com.akobir.wsm.service.OrganizationService;
import com.akobir.wsm.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final OrganizationService organizationService;
    private final WarehouseService warehouseService;
    private final CustomerService customerService;


    @Override
    public List<InvoicePreview> getByOrganizationId(UUID organizationId) {
        getInvoiceById(organizationId);
        return invoiceRepository.findByOrganizationId(organizationId)
                .stream()
                .map(invoiceMapper::mapToPreview)
                .toList();
    }

    @Override
    public List<InvoicePreview> getByOrganizationIdAndWarehouseId(UUID organizationId, UUID warehouseId) {
        getInvoiceById(organizationId);
        return invoiceRepository.findByOrganizationIdAndWarehouseId(organizationId, warehouseId)
                .stream()
                .map(invoiceMapper::mapToPreview)
                .toList();
    }

    @Override
    public Invoice getInvoiceById(UUID invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new CustomNotFoundException("Invoice not found with id: " + invoiceId));
    }

    @Override
    public InvoiceResponse create(InvoiceRequest invoiceRequest) {
        Organization organization = organizationService.getOrganizationById(invoiceRequest.orgId());
        Warehouse warehouse = warehouseService.getWarehouseById(invoiceRequest.warehouseId());
        Customer customer = customerService.getCustomerById(invoiceRequest.customerId());

        Invoice invoice = invoiceMapper.mapToEntity(invoiceRequest);
        invoice.setOrganization(organization);
        invoice.setWarehouse(warehouse);
        invoice.setCustomer(customer);

        return invoiceMapper.mapToResponse(invoiceRepository.save(invoice));
    }

    @Override
    public InvoiceResponse update(UUID uuid, InvoiceRequest invoiceRequest) {
        getInvoiceById(uuid);
        Organization organization = organizationService.getOrganizationById(invoiceRequest.orgId());
        Warehouse warehouse = warehouseService.getWarehouseById(invoiceRequest.warehouseId());
        Customer customer = customerService.getCustomerById(invoiceRequest.customerId());

        Invoice updatedInvoice = invoiceMapper.mapToEntity(invoiceRequest);
        updatedInvoice.setOrganization(organization);
        updatedInvoice.setWarehouse(warehouse);
        updatedInvoice.setCustomer(customer);
        updatedInvoice.setId(uuid);

        return invoiceMapper.mapToResponse(invoiceRepository.save(updatedInvoice));
    }

    @Override
    public InvoiceResponse getById(UUID uuid) {
        return invoiceMapper.mapToResponse(getInvoiceById(uuid));
    }

    @Override
    public List<InvoicePreview> getAllPreviews() {
        return invoiceRepository.findAll()
                .stream()
                .map(invoiceMapper::mapToPreview)
                .toList();
    }

    @Override
    public void delete(UUID uuid) {
        invoiceRepository.delete(getInvoiceById(uuid));
    }
}
