package com.akobir.wsm.dto.response;

public record ErrorResponse(
        String errorPath,
        Integer errorCode,
        Object errorBody
) {
}
