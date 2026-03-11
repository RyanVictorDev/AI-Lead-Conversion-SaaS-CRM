package com.saascrm.application.analytics;

import com.saascrm.domain.user.User;
import com.saascrm.infrastructure.persistence.AnalyticsDailyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnalyticsService {

    private final AnalyticsDailyRepository analyticsDailyRepository;

    public AnalyticsService(AnalyticsDailyRepository analyticsDailyRepository) {
        this.analyticsDailyRepository = analyticsDailyRepository;
    }

    @Transactional(readOnly = true)
    public AnalyticsSummaryResponse getSummary(LocalDate start, LocalDate end, User currentUser) {
        var list = analyticsDailyRepository.findByCompanyIdAndDateBetween(
                currentUser.getCompany().getId(),
                start,
                end
        );

        long totalLeads = list.stream().mapToLong(a -> a.getLeads() != null ? a.getLeads() : 0L).sum();
        long totalConversions = list.stream().mapToLong(a -> a.getConversions() != null ? a.getConversions() : 0L).sum();
        long totalMessages = list.stream().mapToLong(a -> a.getMessages() != null ? a.getMessages() : 0L).sum();

        double conversionRate = totalLeads > 0 ? (double) totalConversions / totalLeads : 0.0;
        double messagesPerLead = totalLeads > 0 ? (double) totalMessages / totalLeads : 0.0;

        return new AnalyticsSummaryResponse(
                totalLeads,
                totalConversions,
                conversionRate,
                totalMessages,
                messagesPerLead
        );
    }

    @Transactional(readOnly = true)
    public List<AnalyticsDailyResponse> getTimeline(LocalDate start, LocalDate end, User currentUser) {
        return analyticsDailyRepository.findByCompanyIdAndDateBetween(
                        currentUser.getCompany().getId(),
                        start,
                        end
                ).stream()
                .map(a -> new AnalyticsDailyResponse(
                        a.getDate(),
                        a.getLeads(),
                        a.getConversions(),
                        a.getMessages()
                ))
                .toList();
    }
}

