package com.saascrm.application.analytics;

import java.time.LocalDate;

public record AnalyticsDailyResponse(
        LocalDate date,
        Long leads,
        Long conversions,
        Long messages
) {
}

