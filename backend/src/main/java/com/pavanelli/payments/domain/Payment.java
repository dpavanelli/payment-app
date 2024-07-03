package com.pavanelli.payments.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Payer first name is mandatory")
    private String firstName;

    @NotBlank(message = "Payer Last name is mandatory")
    private String lastName;

    @NotBlank(message = "Payer zip code is mandatory")
    private String zipCode;

    @NotBlank(message = "Credit card number is mandatory")
    private String cardNumber;

    public Payment() {
    }

    public Payment(String firstName, String lastName, String zipCode, String cardNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
        this.cardNumber = cardNumber;
    }

    public Payment(Long id, String firstName, String lastName, String zipCode, String cardNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
        this.cardNumber = cardNumber;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

}