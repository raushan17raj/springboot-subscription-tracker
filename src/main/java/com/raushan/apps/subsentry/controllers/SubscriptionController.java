package com.raushan.apps.subsentry.controllers;

import com.raushan.apps.subsentry.dto.SubscriptionDto;
import com.raushan.apps.subsentry.dto.SubscriptionRequest;
import com.raushan.apps.subsentry.services.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    // GET /api/subscriptions
    @GetMapping
    public ResponseEntity<List<SubscriptionDto>> getAllSubscriptions() {
        List<SubscriptionDto> subs = subscriptionService.getAllSubscriptions();
        return ResponseEntity.ok(subs);
    }

    // GET /api/subscriptions/{id}
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDto> getSubscriptionById(@PathVariable Long id) {
        SubscriptionDto sub = subscriptionService.getSubscriptionById(id);
        return ResponseEntity.ok(sub);
    }

    // POST /api/subscriptions
    @PostMapping
    public ResponseEntity<SubscriptionDto> createSubscription(@Valid @RequestBody SubscriptionRequest request) {
        System.out.println("request: "+request);
        SubscriptionDto newSub = subscriptionService.createSubscription(request);
        return new ResponseEntity<>(newSub, HttpStatus.CREATED);
    }

    // PUT /api/subscriptions/{id}
    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionDto> updateSubscription(@PathVariable Long id,
                                                              @Valid @RequestBody SubscriptionRequest request) {
        SubscriptionDto updatedSub = subscriptionService.updateSubscription(id, request);
        return ResponseEntity.ok(updatedSub);
    }

    // DELETE /api/subscriptions/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }
}