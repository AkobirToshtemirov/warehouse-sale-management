package com.akobir.wsm.mapper;

public interface Mapper<T, RequestDTO, ResponseDTO, PreviewDTO> {
    T mapToEntity(RequestDTO requestDTO);

    PreviewDTO mapToPreview(T entity);

    ResponseDTO mapToResponse(T entity);
}
