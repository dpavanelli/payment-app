package com.pavanelli.payments.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pending_notifications")
public class PendingNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private Long paymentId;
    private Integer retries = 0;
    private LocalDateTime createdAt = LocalDateTime.now();

    public PendingNotification() {
    }

    public PendingNotification(String url, Long paymentId) {
        this.url = url;
        this.paymentId = paymentId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }
}
