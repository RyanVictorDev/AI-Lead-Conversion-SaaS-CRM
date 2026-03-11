package com.saascrm.application.pipeline;

import com.saascrm.domain.pipeline.Pipeline;
import com.saascrm.domain.pipeline.Stage;
import com.saascrm.domain.user.User;
import com.saascrm.infrastructure.persistence.PipelineRepository;
import com.saascrm.infrastructure.persistence.StageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PipelineService {

    private final PipelineRepository pipelineRepository;
    private final StageRepository stageRepository;

    public PipelineService(PipelineRepository pipelineRepository, StageRepository stageRepository) {
        this.pipelineRepository = pipelineRepository;
        this.stageRepository = stageRepository;
    }

    @Transactional
    public PipelineResponse createPipeline(CreatePipelineRequest request, User currentUser) {
        Pipeline pipeline = new Pipeline();
        pipeline.setName(request.name());
        pipeline.setCompany(currentUser.getCompany());

        Pipeline saved = pipelineRepository.save(pipeline);
        return new PipelineResponse(saved.getId(), saved.getName(), List.of());
    }

    @Transactional
    public StageResponse addStage(Long pipelineId, CreateStageRequest request, User currentUser) {
        Pipeline pipeline = pipelineRepository.findById(pipelineId)
                .orElseThrow(() -> new IllegalArgumentException("Pipeline not found"));

        if (!pipeline.getCompany().getId().equals(currentUser.getCompany().getId())) {
            throw new IllegalArgumentException("Pipeline does not belong to current company");
        }

        Stage stage = new Stage();
        stage.setPipeline(pipeline);
        stage.setName(request.name());
        stage.setOrder(request.order());

        Stage saved = stageRepository.save(stage);
        return new StageResponse(saved.getId(), saved.getName(), saved.getOrder());
    }

    @Transactional(readOnly = true)
    public List<PipelineResponse> listPipelines(User currentUser) {
        return pipelineRepository.findByCompanyId(currentUser.getCompany().getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PipelineResponse getDefaultPipeline(User currentUser) {
        Pipeline pipeline = pipelineRepository.findFirstByCompanyId(currentUser.getCompany().getId())
                .orElseThrow(() -> new IllegalArgumentException("No pipeline configured for company"));
        return toResponse(pipeline);
    }

    private PipelineResponse toResponse(Pipeline pipeline) {
        var stages = stageRepository.findByPipelineIdOrderByOrderAsc(pipeline.getId())
                .stream()
                .map(s -> new StageResponse(s.getId(), s.getName(), s.getOrder()))
                .toList();

        return new PipelineResponse(
                pipeline.getId(),
                pipeline.getName(),
                stages
        );
    }
}

