package com.saascrm.application.pipeline;

import java.util.List;

public record PipelineResponse(
        Long id,
        String name,
        List<StageResponse> stages
) {
}

