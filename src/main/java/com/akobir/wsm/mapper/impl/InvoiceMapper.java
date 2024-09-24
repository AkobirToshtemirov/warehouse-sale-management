package com.akobir.wsm.mapper.impl;

import com.akobir.wsm.dto.preview.IncomingProductPreview;
import com.akobir.wsm.dto.preview.InvoicePreview;
import com.akobir.wsm.dto.preview.OutgoingProductPreview;
import com.akobir.wsm.dto.request.InvoiceRequest;
import com.akobir.wsm.dto.response.InvoiceResponse;
import com.akobir.wsm.entity.Invoice;
import com.akobir.wsm.entity.enums.InvoiceStatus;
import com.akobir.wsm.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InvoiceMapper implements Mapper<Invoice, InvoiceRequest, InvoiceResponse, InvoicePreview> {

    private final OrganizationMapper organizationMapper;
    private final CustomerMapper customerMapper;
    private final WarehouseMapper warehouseMapper;
    private final IncomingProductMapper incomingProductMapper;
    private final OutgoingProductMapper outgoingProductMapper;

    @Override
    public Invoice mapToEntity(InvoiceRequest invoiceRequest) {
        if (invoiceRequest == null) return null;

        Invoice invoice = new Invoice();
        invoice.setNum(invoiceRequest.num());
        invoice.setStatus(InvoiceStatus.CREATED);
        return invoice;
    }

    @Override
    public InvoicePreview mapToPreview(Invoice invoice) {
        if (invoice == null) return null;

        return new InvoicePreview(
                invoice.getId(),
                invoice.getNum(),
                invoice.getStatus().name()
        );
    }

    @Override
    public InvoiceResponse mapToResponse(Invoice invoice) {
        if (invoice == null) return null;

        List<IncomingProductPreview> incomingProductPreviews = invoice.getIncomingProducts()
                .stream()
                .map(incomingProductMapper::mapToPreview)
                .toList();

        List<OutgoingProductPreview> outgoingProductPreviews = invoice.getOutgoingProducts()
                .stream()
                .map(outgoingProductMapper::mapToPreview)
                .toList();

        return new InvoiceResponse(
                invoice.getId(),
                invoice.getNum(),
                invoice.getStatus().name(),
                customerMapper.mapToPreview(invoice.getCustomer()),
                organizationMapper.mapToPreview(invoice.getOrganization()),
                warehouseMapper.mapToPreview(invoice.getWarehouse()),
                incomingProductPreviews,
                outgoingProductPreviews,
                invoice.isDeleted(),
                invoice.getCreatedAt(),
                invoice.getUpdatedAt()
        );
    }
}
