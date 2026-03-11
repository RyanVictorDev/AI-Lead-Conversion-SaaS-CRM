package com.saascrm.infrastructure.persistence;

import com.saascrm.domain.automation.AutomationRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutomationRuleRepository extends JpaRepository<AutomationRule, Long> {

    List<AutomationRule> findByCompanyId(Long companyId);

    List<AutomationRule> findByCompanyIdAndTriggerEvent(Long companyId, String triggerEvent);
}

