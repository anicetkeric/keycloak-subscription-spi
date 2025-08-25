package com.bootlabs.dto;

import java.time.LocalDateTime;

/**
 * Data transfer object representing input data
 * for creating or updating a subscription.
 */
public class SubscriptionModel {

    private String userId;
    private PlanTypeEnum plan;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public SubscriptionModel() {}

    public SubscriptionModel(String userId, PlanTypeEnum plan, LocalDateTime startDate, LocalDateTime endDate) {
        this.userId = userId;
        this.plan = plan;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PlanTypeEnum getPlan() {
        return plan;
    }

    public void setPlan(PlanTypeEnum plan) {
        this.plan = plan;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
