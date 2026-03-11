package com.saascrm.application.analytics;

public record AnalyticsSummaryResponse(
        Long totalLeads,
        Long totalConversions,
        Double conversionRate,
        Long totalMessages,
        Double messagesPerLead
) {
}

