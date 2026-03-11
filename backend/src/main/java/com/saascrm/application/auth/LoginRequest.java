package com.saascrm.application.auth;

public record LoginRequest(
        String email,
        String password
) {
}

