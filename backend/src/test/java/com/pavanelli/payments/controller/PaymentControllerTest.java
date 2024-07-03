package com.pavanelli.payments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pavanelli.payments.api.PaymentController;
import com.pavanelli.payments.api.dto.PaymentDTO;
import com.pavanelli.payments.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreatePaymentValid() throws Exception {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setFirstName("John");
        paymentDTO.setLastName("Doe");
        paymentDTO.setZipCode("12345");
        paymentDTO.setCardNumber("1234 5678 1234 5678");

        when(paymentService.createPayment(any(PaymentDTO.class))).thenReturn(paymentDTO);

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreatePaymentEmptyFirstName() throws Exception {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setFirstName("");
        paymentDTO.setLastName("Doe");
        paymentDTO.setZipCode("12345");
        paymentDTO.setCardNumber("1234 5678 1234 5678");

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreatePaymentEmptyLastName() throws Exception {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setFirstName("John");
        paymentDTO.setLastName("");
        paymentDTO.setZipCode("12345");
        paymentDTO.setCardNumber("1234 5678 1234 5678");

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreatePaymentEmptyZipCode() throws Exception {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setFirstName("John");
        paymentDTO.setLastName("Doe");
        paymentDTO.setZipCode("");
        paymentDTO.setCardNumber("1234 5678 1234 5678");

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreatePaymentEmptyCardNumber() throws Exception {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setFirstName("John");
        paymentDTO.setLastName("Doe");
        paymentDTO.setZipCode("12345");
        paymentDTO.setCardNumber("");

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentDTO)))
                .andExpect(status().isBadRequest());
    }
}
