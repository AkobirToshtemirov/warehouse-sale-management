package com.akobir.wsm.config;

import com.akobir.wsm.config.security.service.CustomUserDetails;
import com.akobir.wsm.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<User> {

    @Override
    public Optional<User> getCurrentAuditor() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            User user = ((CustomUserDetails) principal).user();
            return Optional.of(user);
        }

        return Optional.empty();
    }
}