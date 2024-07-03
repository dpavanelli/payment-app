package com.pavanelli.payments.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class PaymentDTO {

    @Schema(description = "First name of the payer", example = "John")
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @Schema(description = "Last name of the payer", example = "Doe")
    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Schema(description = "Zip code of the payer", example = "12345")
    @NotBlank(message = "Zip code is mandatory")
    private String zipCode;

    @Schema(description = "Card number of the payer", example = "1234 5678 9012 3456")
    @NotBlank(message = "Card number is mandatory")
    private String cardNumber;

    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
