package com.raushan.apps.subsentry.services;

import com.raushan.apps.subsentry.entities.Subscription;
import com.raushan.apps.subsentry.repositories.SubscriptionRepository;
import com.raushan.apps.subsentry.services.MailService;
import com.raushan.apps.subsentry.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private MailService mailService;

    /**
     * Runs every day at 8:00 AM. "0 0 8 * * ?"
     * Checks for trials ending in 1, 2, or 3 days.
     */
    @Scheduled(cron = "${scheduler.cron}")
    @Transactional
    @Override
    public void sendTrialReminders() {
        LocalDate today = LocalDate.now();
        System.out.println("Running reminder check for: " + today); // Logging

        // --- Find trials ending in 1, 2, or 3 days ---
        LocalDate datePlus1 = today.plusDays(1);
        LocalDate datePlus2 = today.plusDays(2);
        LocalDate datePlus3 = today.plusDays(3);

        // Fetch trials for each of the next 3 days
        List<Subscription> trialsEndingTomorrow = subscriptionRepository.findAllByIsTrialTrueAndTrialEndDate(datePlus1);
        List<Subscription> trialsEndingIn2Days = subscriptionRepository.findAllByIsTrialTrueAndTrialEndDate(datePlus2);
        List<Subscription> trialsEndingIn3Days = subscriptionRepository.findAllByIsTrialTrueAndTrialEndDate(datePlus3);

        // --- Combine the lists (or process separately) ---
        // For simplicity, let's process each list
        processReminders(trialsEndingTomorrow, 1);
        processReminders(trialsEndingIn2Days, 2);
        processReminders(trialsEndingIn3Days, 3);
    }

    /**
     * Helper method to send emails for a list of subscriptions.
     * @param subscriptions List of subscriptions ending on a specific day.
     * @param daysLeft The number of days remaining.
     */
    private void processReminders(List<Subscription> subscriptions, int daysLeft) {
        if (subscriptions.isEmpty()) {
            System.out.println("No trials found ending in " + daysLeft + " day(s)."); // Logging
            return;
        }

        System.out.println("Found " + subscriptions.size() + " trials ending in " + daysLeft + " day(s)."); // Logging

        for (Subscription sub : subscriptions) {
            String userEmail = sub.getUser().getEmail();
            String subject = String.format("Reminder: Trial for '%s' ends in %d day(s)!", sub.getName(), daysLeft);
            String body = String.format(
                    "Hi %s,\n\n" +
                            "This is a reminder that your free trial for '%s' will end in %d day(s), on %s.\n\n" +
                            "If you wish to avoid being charged, please cancel your subscription before then.\n\n" +
                            "Thanks,\n" +
                            "The SubSentry Team",
                    sub.getUser().getFullName() != null ? sub.getUser().getFullName() : "there",
                    sub.getName(),
                    daysLeft,
                    sub.getTrialEndDate()
            );

            mailService.sendSimpleMessage(userEmail, subject, body);
        }
    }
}