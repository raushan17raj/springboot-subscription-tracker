package com.raushan.apps.subsentry.services;

public interface MailService {

    /**
     * Sends a simple plain text email.
     * @param to The recipient's email address.
     * @param subject The email subject.
     * @param body The plain text email body.
     */
    void sendSimpleMessage(String to, String subject, String body);
}