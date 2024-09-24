package com.akobir.wsm.dto.response;

import java.time.LocalDate;

public record RemainingProductByDateResponse(
        LocalDate startDate,
        LocalDate endDate,
        Integer quantity
) {
}
