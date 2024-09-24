package com.akobir.wsm.controller;

import com.akobir.wsm.dto.response.ApiMessage;
import com.akobir.wsm.service.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @Operation(summary = "Retrieve an attachment by ID",
            description = "Returns the requested image attachment based on its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Attachment not found")
    })
    @GetMapping("/{imgId}")
    public ResponseEntity<FileUrlResource> getAttachmentById(
            @Parameter(description = "ID of the image to retrieve", required = true)
            @PathVariable("imgId") Long imgId) {
        return attachmentService.getImage(imgId);
    }

    @Operation(summary = "Upload a new attachment",
            description = "Uploads a new image attachment and returns its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file format")
    })
    @PostMapping(value = "/upload", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<Long> uploadAttachment(
            @Parameter(description = "Image file to upload", required = true)
            @RequestParam("img") MultipartFile img) {
        return ResponseEntity.ok(attachmentService.upload(img));
    }

    @Operation(summary = "Delete an attachment by ID",
            description = "Deletes the specified attachment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attachment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Attachment not found")
    })
    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<ApiMessage> deleteAttachmentById(
            @Parameter(description = "ID of the attachment to delete", required = true)
            @PathVariable("attachmentId") Long attachmentId) {
        return ResponseEntity.ok(attachmentService.deleteById(attachmentId));
    }
}
