package com.saascrm.infrastructure.persistence;

import com.saascrm.domain.analytics.AnalyticsDaily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AnalyticsDailyRepository extends JpaRepository<AnalyticsDaily, Long> {

    List<AnalyticsDaily> findByCompanyIdAndDateBetween(Long companyId, LocalDate start, LocalDate end);
}

