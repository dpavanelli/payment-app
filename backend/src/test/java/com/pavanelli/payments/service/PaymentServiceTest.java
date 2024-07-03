package com.pavanelli.payments.service;

import com.pavanelli.payments.api.dto.PaymentDTO;
import com.pavanelli.payments.errors.InvalidFieldException;
import com.pavanelli.payments.repository.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private WebhookService webhookService;

    @Mock
    private EncryptionService encryptionService;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSanitizeCardNumber() throws Exception {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setFirstName("John");
        paymentDTO.setLastName("Doe");
        paymentDTO.setZipCode("12345");
        paymentDTO.setCardNumber("1234 5678 1234 5678");

        when(encryptionService.encrypt("1234567812345678")).thenReturn("encryptedCardNumber");
        Assertions.assertDoesNotThrow(() -> paymentService.createPayment(paymentDTO));

        paymentDTO.setCardNumber("1234-5678-1234-5678");
        when(encryptionService.encrypt("1234567812345678")).thenReturn("encryptedCardNumber");
        Assertions.assertDoesNotThrow(() -> paymentService.createPayment(paymentDTO));

        paymentDTO.setCardNumber("1234567812345678");
        when(encryptionService.encrypt("1234567812345678")).thenReturn("encryptedCardNumber");
        Assertions.assertDoesNotThrow(() -> paymentService.createPayment(paymentDTO));
    }

    @Test
    public void testInvalidCardNumberLength() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setFirstName("John");
        paymentDTO.setLastName("Doe");
        paymentDTO.setZipCode("12345");
        paymentDTO.setCardNumber("123456781234567");

        InvalidFieldException exception = assertThrows(InvalidFieldException.class, () -> paymentService.createPayment(paymentDTO));
        assertEquals("Card number must be 16 digits", exception.getMessage());
    }
}
