package com.raushan.apps.subsentry.services;

import com.raushan.apps.subsentry.dto.SubscriptionDto;
import com.raushan.apps.subsentry.dto.SubscriptionRequest;
import java.util.List;

public interface SubscriptionService {

    List<SubscriptionDto> getAllSubscriptions();

    SubscriptionDto getSubscriptionById(Long id);

    SubscriptionDto createSubscription(SubscriptionRequest request);

    SubscriptionDto updateSubscription(Long id, SubscriptionRequest request);

    void deleteSubscription(Long id);
}