package com.raushan.apps.subsentry.repositories;

import com.raushan.apps.subsentry.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    /**
     * Finds all subscriptions that are trials AND are ending on a specific date.
     * Used by the NotificationService's scheduled job.
     * @param trialEndDate The date to check for.
     * @return A list of subscriptions that match.
     */
    List<Subscription> findAllByIsTrialTrueAndTrialEndDate(LocalDate trialEndDate);

    /**
     * Finds all subscriptions belonging to a specific user by their ID.
     */
    List<Subscription> findAllByUserId(Long userId);

    /**
     * Finds a single subscription by its ID AND the user's ID.
     * This is a crucial security check to ensure a user can't
     * fetch another user's subscription by guessing the ID.
     */
    Optional<Subscription> findByIdAndUserId(Long id, Long userId);

}