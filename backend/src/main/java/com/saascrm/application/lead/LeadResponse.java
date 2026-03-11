package com.saascrm.application.lead;

public record LeadResponse(
        Long id,
        String name,
        String phone,
        String email,
        String status
) {
}

