package com.raushan.apps.subsentry.dto;

import com.raushan.apps.subsentry.entities.BillingCycle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO for handling Create and Update requests from the user.
 */
@Data
public class SubscriptionRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be 0 or more")
    private Double price;

    @NotNull(message = "Billing cycle is required")
    private BillingCycle billingCycle;

    private LocalDate nextBillingDate;

    private boolean isTrial = false;

    private LocalDate trialEndDate;

    private String category;
}