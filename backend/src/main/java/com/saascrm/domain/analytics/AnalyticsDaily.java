package com.saascrm.domain.analytics;

import com.saascrm.domain.company.Company;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "analytics_daily")
public class AnalyticsDaily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Long leads;

    @Column(nullable = false)
    private Long conversions;

    @Column(nullable = false)
    private Long messages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getLeads() {
        return leads;
    }

    public void setLeads(Long leads) {
        this.leads = leads;
    }

    public Long getConversions() {
        return conversions;
    }

    public void setConversions(Long conversions) {
        this.conversions = conversions;
    }

    public Long getMessages() {
        return messages;
    }

    public void setMessages(Long messages) {
        this.messages = messages;
    }
}

