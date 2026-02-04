package com.pgf.user_service.dto;

public record SignUpRequest(
        String email,
        String password,
        String role
) {}

