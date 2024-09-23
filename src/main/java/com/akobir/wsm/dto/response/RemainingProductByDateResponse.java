package com.akobir.wsm.dto.response;

import java.time.LocalDateTime;

public record RemainingProductByDateResponse(
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer quantity
) {
}
