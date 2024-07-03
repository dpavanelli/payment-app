package com.pavanelli.payments.api;

import com.pavanelli.payments.api.dto.PaymentDTO;
import com.pavanelli.payments.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/api/payments")
@Validated
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "Create a new payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment created successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentDTO.class),
                    examples = @ExampleObject(value = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"zipCode\":\"12345\",\"cardNumber\":\"1234 5678 9012 3456\"}")
            )),
            @ApiResponse(responseCode = "400", description = "Invalid payment request", content = @Content(
                    mediaType = "application/json"
            )),
            @ApiResponse(responseCode = "500", description = "Unexpected server internal error", content = @Content(
                    mediaType = "application/json"
            ))
    })
    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@Valid @RequestBody PaymentDTO payment) {
        return new ResponseEntity<>(paymentService.createPayment(payment), HttpStatus.CREATED);
    }

}