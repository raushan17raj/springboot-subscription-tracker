package com.raushan.apps.subsentry.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Enumerated(EnumType.STRING) // Stores as "MONTHLY", "YEARLY"
    @Column(nullable = false)
    private BillingCycle billingCycle;

    @Column
    private LocalDate nextBillingDate;

    @Column
    private boolean isTrial;

    @Column
    private LocalDate trialEndDate;

    @Column
    private String category; // e.g., "Entertainment", "Work"

    /** Many subscriptions can belongs to one user.
     * This adds a "user_id" column to this table, which is the foreign key.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
}