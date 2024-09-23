package com.akobir.wsm.service;

import java.util.List;

public interface BaseService<T, ID, RequestDTO, ResponseDTO, PreviewDTO> {
    ResponseDTO create(RequestDTO requestDTO);

    ResponseDTO update(ID id, RequestDTO requestDTO);

    ResponseDTO getById(ID id);

    List<PreviewDTO> getAllPreviews();

    void delete(ID id);
}

