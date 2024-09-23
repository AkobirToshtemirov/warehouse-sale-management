package com.akobir.wsm.dto.response;

import com.akobir.wsm.dto.preview.WarehousePreview;

public record RemainingProductByWarehouseResponse(
        WarehousePreview warehouse,
        Integer quantity
) {
}
