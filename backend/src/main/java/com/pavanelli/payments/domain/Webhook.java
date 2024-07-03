package com.pavanelli.payments.domain;

import com.pavanelli.payments.validation.ValidUrl;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "webhooks")
public class Webhook {

    @Id
    @NotBlank(message = "URL is mandatory")
    @ValidUrl
    private String url;

    public Webhook() {
    }

    public Webhook(String url) {
        this.url = url.trim();
    }

    public String getUrl() {
        return url.trim();
    }

    public void setUrl(String url) {
        this.url = url.trim();
    }

}