/*
package com.akobir.wsm.initializer;

import com.akobir.wsm.entity.Organization;
import com.akobir.wsm.entity.User;
import com.akobir.wsm.repository.OrganizationRepository;
import com.akobir.wsm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Organization organization = new Organization();
        organization.setName("INITIAL_ORG");
        organization.setTin("123456789");
        organization.setCreatedAt(LocalDateTime.now());
        organization.setUpdatedAt(LocalDateTime.now());

        Organization savedOrg = organizationRepository.save(organization);

        User user = new User();
        user.setUsername("initialuser");
        user.setPassword(passwordEncoder.encode("12345678"));
        user.setEmail("user@example.com");
        user.setOrganization(savedOrg);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
    }
}
*/
