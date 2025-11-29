package com.raushan.apps.subsentry.services;

public interface NotificationService {

    /**
     * Checks for upcoming trial expirations and sends reminder emails.
     * This method will be triggered by a scheduler.
     */
    void sendTrialReminders();
}