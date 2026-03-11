package com.saascrm.application.lead;

public record CreateLeadRequest(
        String name,
        String phone,
        String email
) {
}

