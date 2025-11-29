package com.raushan.apps.subsentry.dto;

import com.raushan.apps.subsentry.entities.BillingCycle;
import com.raushan.apps.subsentry.entities.Subscription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for sending Subscription data back to the user (API Response).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    
    private Long id;
    private String name;
    private double price;
    private BillingCycle billingCycle;
    private LocalDate nextBillingDate;
    private boolean isTrial;
    private LocalDate trialEndDate;
    private String category;

    /**
     * Helper method to convert an Entity to this DTO.
     * @param entity The Subscription entity from the database.
     * @return A SubscriptionDto.
     */
    public static SubscriptionDto fromEntity(Subscription entity) {
        return new SubscriptionDto(
            entity.getId(),
            entity.getName(),
            entity.getPrice(),
            entity.getBillingCycle(),
            entity.getNextBillingDate(),
            entity.isTrial(),
            entity.getTrialEndDate(),
            entity.getCategory()
        );
    }
}