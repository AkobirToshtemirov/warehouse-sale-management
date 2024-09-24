package com.akobir.wsm.mapper.impl;

import com.akobir.wsm.dto.preview.OrganizationPreview;
import com.akobir.wsm.dto.preview.UserPreview;
import com.akobir.wsm.dto.preview.WarehousePreview;
import com.akobir.wsm.dto.request.UserRequest;
import com.akobir.wsm.dto.response.UserResponse;
import com.akobir.wsm.entity.Attachment;
import com.akobir.wsm.entity.User;
import com.akobir.wsm.mapper.Mapper;
import com.akobir.wsm.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserRequest, UserResponse, UserPreview> {

    private final AttachmentService attachmentService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User mapToEntity(UserRequest userRequest) {
        if (userRequest == null) return null;

        User user = new User();
        user.setUsername(userRequest.username());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setEmail(userRequest.email());
        user.setMainPhoto(userRequest.mainPhoto());
        return user;
    }

    @Override
    public UserPreview mapToPreview(User user) {
        if (user == null) return null;

        return new UserPreview(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    @Override
    public UserResponse mapToResponse(User user) {
        if (user == null) return null;

        OrganizationPreview organizationPreview = new OrganizationPreview(
                user.getOrganization().getId(),
                user.getOrganization().getName()
        );

        WarehousePreview warehousePreview = null;
        if (user.getWarehouse() != null) {
            warehousePreview = new WarehousePreview(
                    user.getWarehouse().getId(),
                    user.getWarehouse().getName()
            );
        }

        Attachment attachment = user.getAttachment();
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getMainPhoto(),
                organizationPreview,
                warehousePreview,
                user.isDeleted(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                attachment == null ? null : attachmentService.toResponse(attachment)
        );
    }
}
