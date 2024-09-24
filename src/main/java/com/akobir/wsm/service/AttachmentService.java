package com.akobir.wsm.service;

import com.akobir.wsm.dto.response.ApiMessage;
import com.akobir.wsm.dto.response.AttachmentResponse;
import com.akobir.wsm.entity.Attachment;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    Long upload(MultipartFile img);

    ApiMessage deleteById(Long attachmentId);

    ResponseEntity<FileUrlResource> getImage(Long imgId);

    Attachment getById(Long attachmentId);

    AttachmentResponse toResponse(Attachment attachment);
}