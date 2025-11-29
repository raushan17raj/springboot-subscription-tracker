package com.raushan.apps.subsentry.services;

import com.raushan.apps.subsentry.dto.SubscriptionDto;
import com.raushan.apps.subsentry.dto.SubscriptionRequest;
import com.raushan.apps.subsentry.entities.Subscription;
import com.raushan.apps.subsentry.entities.User;
import com.raushan.apps.subsentry.exception.ResourceNotFoundException;
import com.raushan.apps.subsentry.repositories.SubscriptionRepository;
import com.raushan.apps.subsentry.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Helper method to get the currently authenticated user.
     * This is the core of our user-specific logic.
     */
    private User getLoggedInUser() {
        // 1. Get the Authentication object from the SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 2. Get the username (which is our email)
        String userEmail = authentication.getName();

        // 3. Find the user in the database
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
    }


    @Override
    public List<SubscriptionDto> getAllSubscriptions() {
        // Get the logged-in user
        User user = getLoggedInUser();

        // Use their ID to find *only* their subscriptions
        return subscriptionRepository.findAllByUserId(user.getId())
                .stream()
                .map(SubscriptionDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionDto getSubscriptionById(Long id) {
        User user = getLoggedInUser();

        // Use the secure finder method
        Subscription sub = subscriptionRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Subscription", "id", id));
        return SubscriptionDto.fromEntity(sub);
    }

    @Override
    public SubscriptionDto createSubscription(SubscriptionRequest request) {
        // Get the user who is creating this
        User user = getLoggedInUser();

        Subscription newSub = new Subscription();
        newSub.setName(request.getName());
        newSub.setPrice(request.getPrice());
        newSub.setBillingCycle(request.getBillingCycle());
        newSub.setNextBillingDate(request.getNextBillingDate());
        newSub.setTrial(request.isTrial());
        newSub.setTrialEndDate(request.getTrialEndDate());
        newSub.setCategory(request.getCategory());

        // --- Set the user link ---
        newSub.setUser(user);

        Subscription savedSub = subscriptionRepository.save(newSub);
        return SubscriptionDto.fromEntity(savedSub);
    }

    @Override
    public SubscriptionDto updateSubscription(Long id, SubscriptionRequest request) {
        User user = getLoggedInUser();

        // Find the sub *securely* (checks both subId and userId)
        Subscription existingSub = subscriptionRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Subscription", "id", id));

        // Update fields
        existingSub.setName(request.getName());
        existingSub.setPrice(request.getPrice());
        existingSub.setCategory(request.getCategory());

        Subscription updatedSub = subscriptionRepository.save(existingSub);
        return SubscriptionDto.fromEntity(updatedSub);
    }

    @Override
    public void deleteSubscription(Long id) {
        User user = getLoggedInUser();

        // Find the sub *securely*
        Subscription sub = subscriptionRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Subscription", "id", id));

        subscriptionRepository.delete(sub);
    }
}