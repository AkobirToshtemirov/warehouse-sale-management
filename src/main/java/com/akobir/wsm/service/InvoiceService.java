package com.akobir.wsm.service;

import com.akobir.wsm.dto.preview.InvoicePreview;
import com.akobir.wsm.dto.request.InvoiceRequest;
import com.akobir.wsm.dto.response.InvoiceResponse;
import com.akobir.wsm.entity.Invoice;

import java.util.List;
import java.util.UUID;

public interface InvoiceService extends BaseService<Invoice, UUID, InvoiceRequest, InvoiceResponse, InvoicePreview> {
    List<InvoicePreview> getByOrganizationId(UUID organizationId);

    List<InvoicePreview> getByOrganizationIdAndWarehouseId(UUID organizationId, UUID warehouseId);

    Invoice getInvoiceById(UUID invoiceId);
}
