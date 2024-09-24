package com.akobir.wsm.service;

import com.akobir.wsm.dto.preview.UserPreview;
import com.akobir.wsm.dto.request.UserRequest;
import com.akobir.wsm.dto.response.UserResponse;
import com.akobir.wsm.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService extends BaseService<User, UUID, UserRequest, UserResponse, UserPreview> {
    UserResponse getCachedUserByUsername(String username);

    List<UserPreview> getAllByOrganization(UUID orgId);

    User getByUsername(String username);
}
