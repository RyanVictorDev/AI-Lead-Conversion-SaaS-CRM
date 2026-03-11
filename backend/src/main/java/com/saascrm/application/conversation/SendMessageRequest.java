package com.saascrm.application.conversation;

public record SendMessageRequest(
        String sender,
        String content
) {
}

