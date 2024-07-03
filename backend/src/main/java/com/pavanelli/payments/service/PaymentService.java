package com.pavanelli.payments.service;

import com.pavanelli.payments.api.dto.PaymentDTO;
import com.pavanelli.payments.domain.Payment;
import com.pavanelli.payments.errors.InvalidFieldException;
import com.pavanelli.payments.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final int CREDIT_CARD_EXPECTED_DIGITS = 16;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private WebhookService webhookService;

    @Transactional
    public PaymentDTO createPayment(PaymentDTO payment) {
        String sanitizedCardNumber = sanitizeCardNumber(payment.getCardNumber());
        String encryptedCardNumber = encryptionService.encrypt(sanitizedCardNumber);
        Payment savedPayment = paymentRepository.save(new Payment(
                payment.getFirstName(),
                payment.getLastName(),
                payment.getZipCode(),
                encryptedCardNumber)
        );
        webhookService.notifyWebhooks(savedPayment);
        return payment;
    }

    private String sanitizeCardNumber(String cardNumber) {
        if (cardNumber == null) {
            throw new InvalidFieldException("cardNumber", "Credit card number is mandatory");
        }
        String creditCardDigits = cardNumber.replaceAll("[^\\d]", "");
        if (creditCardDigits.length() != CREDIT_CARD_EXPECTED_DIGITS) {
            throw new InvalidFieldException("cardNumber", "Card number must be " + CREDIT_CARD_EXPECTED_DIGITS + " digits");
        }
        return creditCardDigits;
    }

}
