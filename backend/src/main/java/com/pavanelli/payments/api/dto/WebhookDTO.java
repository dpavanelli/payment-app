package com.pavanelli.payments.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class WebhookDTO {

    @NotBlank(message = "URL is mandatory")
    @Pattern(
            regexp = "^(https?)://(([a-zA-Z0-9_-]+\\.)+[a-zA-Z]{2,6}|(\\d{1,3}\\.){3}\\d{1,3})(:\\d+)?(/[^\\s]*)?$",
            message = "Invalid URL"
    )
    private String url;

    // Getters and setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}