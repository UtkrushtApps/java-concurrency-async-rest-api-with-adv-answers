package com.example.notificationapi.controller;

import com.example.notificationapi.model.NotificationRequest;
import com.example.notificationapi.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Future;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        Future<Boolean> futureResult = notificationService.sendNotificationAsync(request);
        logger.info("Notification processing submitted for recipient: {}", request.getRecipient());
        // Return 202 Accepted for async processing
        return ResponseEntity.accepted().body("Notification request accepted and processing asynchronously.");
    }

    @GetMapping("/stats")
    public ResponseEntity<String> stats() {
        int count = notificationService.getNotificationsProcessed();
        return ResponseEntity.ok("Notifications processed: " + count);
    }

    @GetMapping("/healthz")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}
