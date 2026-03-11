package com.saascrm.infrastructure.persistence;

import com.saascrm.domain.pipeline.Pipeline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PipelineRepository extends JpaRepository<Pipeline, Long> {

    List<Pipeline> findByCompanyId(Long companyId);

    Optional<Pipeline> findFirstByCompanyId(Long companyId);
}

