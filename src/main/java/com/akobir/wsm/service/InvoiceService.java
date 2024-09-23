package com.akobir.wsm.service;

import com.akobir.wsm.dto.preview.IncomingProductPreview;
import com.akobir.wsm.dto.preview.InvoicePreview;
import com.akobir.wsm.dto.preview.OutgoingProductPreview;
import com.akobir.wsm.dto.request.InvoiceRequest;
import com.akobir.wsm.dto.response.InvoiceResponse;
import com.akobir.wsm.entity.Invoice;

import java.util.List;
import java.util.UUID;

public interface InvoiceService extends BaseService<Invoice, UUID, InvoiceRequest, InvoiceResponse, InvoicePreview> {
    List<IncomingProductPreview> getIncomingProductsByInvoiceId(UUID invoiceId);

    List<OutgoingProductPreview> getOutgoingProductsByInvoiceId(UUID invoiceId);
}
