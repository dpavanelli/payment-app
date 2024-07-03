package com.pavanelli.payments.service;

import com.pavanelli.payments.api.dto.WebhookDTO;
import com.pavanelli.payments.domain.Payment;
import com.pavanelli.payments.domain.Webhook;
import com.pavanelli.payments.errors.DuplicateEntityException;
import com.pavanelli.payments.errors.InvalidFieldException;
import com.pavanelli.payments.repository.WebhookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class WebhookService {

    @Autowired
    private WebhookRepository webhookRepository;

    @Autowired
    private NotificationService notificationService;

    public WebhookService() {
    }

    public WebhookDTO registerWebhook(WebhookDTO webhook) {
        validate(webhook);
        if (webhookRepository.existsByUrl(webhook.getUrl())) {
            throw new DuplicateEntityException("Webhook already registered: " + webhook.getUrl());
        }
        webhookRepository.save(new Webhook(webhook.getUrl()));
        return webhook;
    }

    public void notifyWebhooks(Payment payment) {
        List<Webhook> webhooks = webhookRepository.findAll();
        webhooks.forEach(webhook -> notificationService.dispatch(webhook, payment));
    }

    private void validate(WebhookDTO webhook) {
        try {
            new URL(webhook.getUrl());
        } catch (MalformedURLException e) {
            throw new InvalidFieldException("url", "URL informed is invalid");
        }
    }

}