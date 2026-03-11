package com.saascrm.application.pipeline;

public record CreateStageRequest(
        String name,
        Integer order
) {
}

