package com.akobir.wsm.service.impl;

import com.akobir.wsm.dto.response.ApiMessage;
import com.akobir.wsm.dto.response.AttachmentResponse;
import com.akobir.wsm.entity.Attachment;
import com.akobir.wsm.exception.CustomNotFoundException;
import com.akobir.wsm.repository.AttachmentRepository;
import com.akobir.wsm.service.AttachmentService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:file.properties")
public class AttachmentServiceImpl implements AttachmentService {

    private static final Logger log = LoggerFactory.getLogger(AttachmentServiceImpl.class);
    private final AttachmentRepository attachmentRepository;
    @Value("${file.base.url}")
    private String BASE_FILE_URL;

    @Value("${load-image.url}")
    private String LOAD_IMAGE_URL;

    @PostConstruct
    public void init() {
        if (!Files.exists(Paths.get(BASE_FILE_URL))) {
            try {
                Files.createDirectory(Paths.get(BASE_FILE_URL));
            } catch (IOException e) {
                log.error("Failed to create base file url directory");
            }
        }
    }

    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public Long upload(MultipartFile img) {
        Attachment attachment = uploadAttachmentToDb(img);
        uploadToStorage(img, attachment);
        return attachment.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiMessage deleteById(Long attachmentId) {
        Attachment attachment = getById(attachmentId);

        try {
            deleteFromStorage(attachment);
            attachmentRepository.delete(attachment);
        } catch (IOException e) {
            return new ApiMessage("Failed to delete attachment");
        }

        return new ApiMessage("Attachment deleted successfully");
    }

    @Override
    public ResponseEntity<FileUrlResource> getImage(Long attachmentId) {
        Attachment attachment = getById(attachmentId);

        try {
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; fileName=" +
                                    UriEncoder.encode(attachment.getFileName()))
                    .contentType(MediaType.parseMediaType(attachment.getContentType()))
                    .body(
                            new FileUrlResource(
                                    attachment.getFileUrl())
                    );
        } catch (MalformedURLException ignored) {
            log.error("Attachment not found with id: {}", attachmentId);
        }

        return ResponseEntity.notFound().build();
    }

    @Override
    public Attachment getById(Long attachmentId) {
        return attachmentRepository.findById(attachmentId)
                .orElseThrow(
                        () -> new CustomNotFoundException("Attachment not found with id: " + attachmentId)
                );
    }

    @Override
    public AttachmentResponse toResponse(Attachment attachment) {
        return new AttachmentResponse(
                attachment.getId(),
                LOAD_IMAGE_URL + attachment.getId()
        );
    }

    private void deleteFromStorage(Attachment attachment) throws IOException {
        Path path = Paths.get(attachment.getFileUrl());
        Files.delete(path);
    }

    private void uploadToStorage(MultipartFile img, Attachment attachment) throws IOException {
        Path path = Paths.get(attachment.getFileUrl());
        Files.write(path, img.getBytes());
    }

    private Attachment uploadAttachmentToDb(MultipartFile file) {
        Attachment attachment = new Attachment();
        String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        attachment.setFileName(file.getOriginalFilename());
        attachment.setExt(extension);
        attachment.setContentType(file.getContentType());
        attachment.setFileUrl(generateFileName(attachment));

        return attachmentRepository.saveAndFlush(attachment);
    }

    private String generateFileName(Attachment attachment) {
        return BASE_FILE_URL + "/" + UUID.randomUUID() + "-" + System.currentTimeMillis() + "." + attachment.getExt();
    }

    private String getExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index > 0)
            return fileName.substring(index + 1);

        return null;
    }
}
