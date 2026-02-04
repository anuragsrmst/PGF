package com.pgf.user_service.service;

import com.pgf.user_service.dto.SignUpRequest;
import com.pgf.user_service.entity.Role;
import com.pgf.user_service.entity.User;
import com.pgf.user_service.repository.RoleRepository;
import com.pgf.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignUpRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }

        Role role = roleRepository.findByName(request.role())
                .orElseThrow(() -> new RuntimeException("Invalid role"));

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.getRoles().add(role);

        userRepository.save(user);
    }
}

