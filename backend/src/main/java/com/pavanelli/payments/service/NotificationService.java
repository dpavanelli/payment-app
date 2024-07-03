package com.pavanelli.payments.service;

import com.pavanelli.payments.domain.Payment;
import com.pavanelli.payments.domain.PendingNotification;
import com.pavanelli.payments.domain.Webhook;
import com.pavanelli.payments.repository.PaymentRepository;
import com.pavanelli.payments.repository.PendingNotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private static final int MAX_RETRIES = 5;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PendingNotificationRepository pendingNotificationRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void dispatch(Webhook webhook, Payment payment) {
        String url = webhook.getUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Payment> request = new HttpEntity<>(payment, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if (response.getStatusCode() != HttpStatus.OK && response.getStatusCode() != HttpStatus.CREATED) {
                throw new IllegalStateException("Webhook response " + response.getStatusCode().value() + ": " + response.getBody());
            }
        } catch (Exception e) {
            logger.warn("Failed to notify webhook {}: {}. A retry will be scheduled for this payment", webhook.getUrl(), e);
            pendingNotificationRepository.save(new PendingNotification(url, payment.getId()));
        }
    }

    public void retryPendingNotifications() {
        List<PendingNotification> pendingNotifications = pendingNotificationRepository.findByRetriesLessThan(MAX_RETRIES);
        for (PendingNotification pendingNotification : pendingNotifications) {
            retryPendingNotification(pendingNotification);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void retryPendingNotification(PendingNotification pendingNotification) {
        Payment payment = paymentRepository.findById(pendingNotification.getPaymentId()).get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Payment> request = new HttpEntity<>(payment, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(pendingNotification.getUrl(), request, String.class);
            if (response.getStatusCode() != HttpStatus.OK && response.getStatusCode() != HttpStatus.CREATED) {
                throw new IllegalStateException("Webhook response " + response.getStatusCode().value() + ": " + response.getBody());
            }
            pendingNotificationRepository.delete(pendingNotification);
        } catch (Exception e) {
            if (pendingNotification.getRetries() < MAX_RETRIES) {
                pendingNotification.setRetries(pendingNotification.getRetries() + 1);
                pendingNotificationRepository.save(pendingNotification);
                logger.warn("Failed to retry notification for payment {}: {}", pendingNotification.getPaymentId(), e);
            } else {
                logger.warn("Permanent failure on retry notification for payment {}: {}", pendingNotification.getPaymentId(), e);
            }
        }
    }
}
