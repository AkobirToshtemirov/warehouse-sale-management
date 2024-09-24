package com.akobir.wsm.config.security.service;

import com.akobir.wsm.entity.User;
import com.akobir.wsm.exception.CustomNotFoundException;
import com.akobir.wsm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomNotFoundException("User not found with the username : %s".formatted(username)));
        return new CustomUserDetails(user);

    }
}
