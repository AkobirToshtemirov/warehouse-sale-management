package com.akobir.wsm.service.impl;

import com.akobir.wsm.dto.preview.UserPreview;
import com.akobir.wsm.dto.request.UserRequest;
import com.akobir.wsm.dto.response.UserResponse;
import com.akobir.wsm.entity.Organization;
import com.akobir.wsm.entity.User;
import com.akobir.wsm.entity.Warehouse;
import com.akobir.wsm.exception.CustomNotFoundException;
import com.akobir.wsm.mapper.impl.UserMapper;
import com.akobir.wsm.repository.UserRepository;
import com.akobir.wsm.service.OrganizationService;
import com.akobir.wsm.service.UserService;
import com.akobir.wsm.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final OrganizationService organizationService;
    private final WarehouseService warehouseService;

    @Override
    @Cacheable(value = "userCache", key = "#username")
    public UserResponse getCachedUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::mapToResponse)
                .orElseThrow(() -> new CustomNotFoundException("User not found with username: " + username));
    }

    @Override
    @Cacheable(value = "userCache", key = "'organization_' + #orgId")
    public List<UserPreview> getAllByOrganization(UUID orgId) {
        return userRepository.findAllByOrganizationId(orgId)
                .stream()
                .map(userMapper::mapToPreview)
                .toList();
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomNotFoundException("User not found with username: " + username));
    }

    @Override
    @Cacheable(value = "userCache", key = "'allUserPreviews'")
    public List<UserPreview> getAllPreviews() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::mapToPreview)
                .toList();
    }

    @Override
    @CacheEvict(value = "userCache", allEntries = true)
    public UserResponse create(UserRequest userRequest) {
        Organization organization = organizationService.getOrganizationById(userRequest.orgId());
        Warehouse warehouse = warehouseService.getWarehouseById(userRequest.warehouseId());

        User user = userMapper.mapToEntity(userRequest);
        user.setOrganization(organization);
        user.setWarehouse(warehouse);
        return userMapper.mapToResponse(userRepository.save(user));
    }

    @Override
    @CacheEvict(value = "userCache", allEntries = true)
    public UserResponse update(UUID uuid, UserRequest userRequest) {
        getUserById(uuid);
        Organization organization = organizationService.getOrganizationById(userRequest.orgId());
        Warehouse warehouse = warehouseService.getWarehouseById(userRequest.warehouseId());

        User updatedUser = userMapper.mapToEntity(userRequest);
        updatedUser.setOrganization(organization);
        updatedUser.setWarehouse(warehouse);
        updatedUser.setId(uuid);
        return userMapper.mapToResponse(userRepository.save(updatedUser));
    }

    @Override
    public UserResponse getById(UUID uuid) {
        return userMapper.mapToResponse(getUserById(uuid));
    }

    @Override
    @CacheEvict(value = "userCache", allEntries = true)
    public void delete(UUID uuid) {
        userRepository.delete(getUserById(uuid));
    }

    private User getUserById(UUID uuid) {
        return userRepository.findById(uuid)
                .orElseThrow(() -> new CustomNotFoundException("User not found with uuid: " + uuid));
    }
}
