package com.saascrm.application.auth;

public record RegisterRequest(
        String companyName,
        String companyEmail,
        String plan,
        String userName,
        String userEmail,
        String password
) {
}

