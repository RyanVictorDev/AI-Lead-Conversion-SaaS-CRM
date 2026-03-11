package com.saascrm.infrastructure.persistence;

import com.saascrm.domain.pipeline.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StageRepository extends JpaRepository<Stage, Long> {

    List<Stage> findByPipelineIdOrderByOrderAsc(Long pipelineId);
}

