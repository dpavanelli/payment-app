package com.pavanelli.payments.service;

import com.pavanelli.payments.api.dto.WebhookDTO;
import com.pavanelli.payments.domain.Payment;
import com.pavanelli.payments.domain.Webhook;
import com.pavanelli.payments.errors.DuplicateEntityException;
import com.pavanelli.payments.errors.InvalidFieldException;
import com.pavanelli.payments.repository.WebhookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class WebhookServiceTest {

    @Mock
    private WebhookRepository webhookRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private WebhookService webhookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterWebhookSuccess() {
        WebhookDTO webhookDTO = new WebhookDTO();
        webhookDTO.setUrl("http://example.com/webhook");

        when(webhookRepository.existsByUrl(webhookDTO.getUrl())).thenReturn(false);

        doAnswer(invocation -> null).when(webhookRepository).save(any(Webhook.class));

        WebhookDTO result = webhookService.registerWebhook(webhookDTO);

        assertEquals(webhookDTO.getUrl(), result.getUrl());
        verify(webhookRepository, times(1)).save(any(Webhook.class));
    }

    @Test
    public void testRegisterWebhookDuplicate() {
        WebhookDTO webhookDTO = new WebhookDTO();
        webhookDTO.setUrl("http://example.com/webhook");

        when(webhookRepository.existsByUrl(webhookDTO.getUrl())).thenReturn(true);

        DuplicateEntityException exception = assertThrows(DuplicateEntityException.class, () -> {
            webhookService.registerWebhook(webhookDTO);
        });

        assertEquals("Webhook already registered: http://example.com/webhook", exception.getMessage());
        verify(webhookRepository, never()).save(any(Webhook.class));
    }

    @Test
    public void testRegisterWebhookInvalidURL() {
        WebhookDTO webhookDTO = new WebhookDTO();
        webhookDTO.setUrl("invalid-url");

        InvalidFieldException exception = assertThrows(InvalidFieldException.class, () -> {
            webhookService.registerWebhook(webhookDTO);
        });

        assertEquals("URL informed is invalid", exception.getMessage());
        verify(webhookRepository, never()).save(any(Webhook.class));
    }

    @Test
    public void testNotifyWebhooks() {
        Payment payment = new Payment();
        List<Webhook> webhooks = Arrays.asList(
                new Webhook("http://example.com/webhook1"),
                new Webhook("http://example.com/webhook2")
        );

        when(webhookRepository.findAll()).thenReturn(webhooks);

        webhookService.notifyWebhooks(payment);

        verify(notificationService, times(webhooks.size())).dispatch(any(Webhook.class), eq(payment));
    }
}
