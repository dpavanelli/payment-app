package com.pavanelli.payments.api;

import com.pavanelli.payments.api.dto.WebhookDTO;
import com.pavanelli.payments.domain.Webhook;
import com.pavanelli.payments.service.WebhookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/webhooks")
@Validated
public class WebhooksController {

    @Autowired
    private WebhookService webhookService;

    @Operation(summary = "Register a new webhook")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Webhook registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid webhook registration request"),
            @ApiResponse(responseCode = "409", description = "Webhook already registered"),
            @ApiResponse(responseCode = "500", description = "Unexpected server internal error")
    })
    @PostMapping
    public ResponseEntity<WebhookDTO> registerWebhook(@Valid @RequestBody WebhookDTO webhook) {
        return new ResponseEntity<>(webhookService.registerWebhook(webhook), HttpStatus.CREATED);
    }

}

