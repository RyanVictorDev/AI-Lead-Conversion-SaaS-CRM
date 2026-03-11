package com.saascrm.application.conversation;

import java.time.OffsetDateTime;

public record MessageResponse(
        Long id,
        String sender,
        String content,
        OffsetDateTime createdAt
) {
}

