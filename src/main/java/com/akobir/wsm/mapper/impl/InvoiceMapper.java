package com.akobir.wsm.mapper.impl;

import com.akobir.wsm.dto.preview.InvoicePreview;
import com.akobir.wsm.dto.request.InvoiceRequest;
import com.akobir.wsm.dto.response.InvoiceResponse;
import com.akobir.wsm.entity.Customer;
import com.akobir.wsm.entity.Invoice;
import com.akobir.wsm.entity.Organization;
import com.akobir.wsm.entity.enums.InvoiceStatus;
import com.akobir.wsm.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvoiceMapper implements Mapper<Invoice, InvoiceRequest, InvoiceResponse, InvoicePreview> {

    private final OrganizationMapper organizationMapper;

    @Override
    public Invoice mapToEntity(InvoiceRequest invoiceRequest) {
        if (invoiceRequest == null) return null;

        Invoice invoice = new Invoice();
        Organization organization = new Organization(); // Fetch from service/repository
        organization.setId(invoiceRequest.orgId());

        Customer customer = new Customer(); // Fetch from service/repository
        customer.setId(invoiceRequest.customerId());

        invoice.setNum(invoiceRequest.num());
        invoice.setOrganization(organization);
        invoice.setCustomer(customer);
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

        return new InvoiceResponse(
                invoice.getId(),
                invoice.getNum(),
                organizationMapper.mapToPreview(invoice.getOrganization()),
                invoice.isDeleted(),
                invoice.getCreatedAt(),
                invoice.getUpdatedAt()
        );
    }
}
