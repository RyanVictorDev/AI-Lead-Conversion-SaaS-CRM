package com.saascrm.domain.feature;

import jakarta.persistence.*;

@Entity
@Table(name = "feature_flag")
public class FeatureFlag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "feature_name", nullable = false, unique = true)
    private String featureName;

    @Column(name = "plan_required", nullable = false)
    private String planRequired;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getPlanRequired() {
        return planRequired;
    }

    public void setPlanRequired(String planRequired) {
        this.planRequired = planRequired;
    }
}

