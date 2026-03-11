package com.saascrm.presentation.api.pipeline;

import com.saascrm.application.pipeline.CreatePipelineRequest;
import com.saascrm.application.pipeline.CreateStageRequest;
import com.saascrm.application.pipeline.PipelineResponse;
import com.saascrm.application.pipeline.PipelineService;
import com.saascrm.application.pipeline.StageResponse;
import com.saascrm.infrastructure.persistence.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pipelines")
public class PipelineController {

    private final PipelineService pipelineService;
    private final UserRepository userRepository;

    public PipelineController(PipelineService pipelineService, UserRepository userRepository) {
        this.pipelineService = pipelineService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<PipelineResponse> createPipeline(
            @RequestBody CreatePipelineRequest request,
            Authentication authentication
    ) {
        var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();
        var response = pipelineService.createPipeline(request, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PipelineResponse>> listPipelines(Authentication authentication) {
        var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();
        var pipelines = pipelineService.listPipelines(user);
        return ResponseEntity.ok(pipelines);
    }

    @GetMapping("/default")
    public ResponseEntity<PipelineResponse> getDefaultPipeline(Authentication authentication) {
        var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();
        var pipeline = pipelineService.getDefaultPipeline(user);
        return ResponseEntity.ok(pipeline);
    }

    @PostMapping("/{pipelineId}/stages")
    public ResponseEntity<StageResponse> addStage(
            @PathVariable Long pipelineId,
            @RequestBody CreateStageRequest request,
            Authentication authentication
    ) {
        var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();
        var response = pipelineService.addStage(pipelineId, request, user);
        return ResponseEntity.ok(response);
    }
}

