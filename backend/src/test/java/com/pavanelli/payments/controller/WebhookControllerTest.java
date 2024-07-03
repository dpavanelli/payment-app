package com.pavanelli.payments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pavanelli.payments.api.WebhooksController;
import com.pavanelli.payments.api.dto.WebhookDTO;
import com.pavanelli.payments.service.WebhookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WebhooksController.class)
public class WebhookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WebhookService webhookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testValidUrl() throws Exception {
        WebhookDTO validWebhook = new WebhookDTO();
        validWebhook.setUrl("http://example.com/webhook");

        mockMvc.perform(post("/api/webhooks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validWebhook)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testValidUrlWithPort() throws Exception {
        WebhookDTO validWebhook = new WebhookDTO();
        validWebhook.setUrl("http://example.com:8080/webhook");

        mockMvc.perform(post("/api/webhooks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validWebhook)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testValidIpUrl() throws Exception {
        WebhookDTO validWebhook = new WebhookDTO();
        validWebhook.setUrl("http://192.168.1.1:8080/webhook");

        mockMvc.perform(post("/api/webhooks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validWebhook)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testInvalidUrl() throws Exception {
        WebhookDTO invalidWebhook = new WebhookDTO();
        invalidWebhook.setUrl("invalid-url");

        mockMvc.perform(post("/api/webhooks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidWebhook)))
                .andExpect(status().isBadRequest());
    }
}
