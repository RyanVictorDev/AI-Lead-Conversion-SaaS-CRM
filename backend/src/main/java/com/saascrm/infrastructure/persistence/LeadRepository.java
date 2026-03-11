package com.saascrm.infrastructure.persistence;

import com.saascrm.domain.lead.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeadRepository extends JpaRepository<Lead, Long> {

    List<Lead> findByCompanyId(Long companyId);
}

