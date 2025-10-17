package com.example.notificationapi.service;

import com.example.notificationapi.model.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    // Example thread-safe counter to illustrate concurrency management
    private final AtomicInteger notificationsProcessed = new AtomicInteger(0);

    @Async("notificationExecutor")
    public Future<Boolean> sendNotificationAsync(NotificationRequest request) {
        // Simulate notification sending (call external API, etc.)
        try {
            // Simulated processing logic
            logger.info("Processing notification to recipient: {}", request.getRecipient());
            Thread.sleep(200); // simulate some IO/blocking work
            notificationsProcessed.incrementAndGet();
            logger.info("Notification sent successfully to: {}", request.getRecipient());
            return new AsyncResult<>(Boolean.TRUE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Notification sending was interrupted", e);
            return new AsyncResult<>(Boolean.FALSE);
        }
    }

    public int getNotificationsProcessed() {
        return notificationsProcessed.get();
    }
}
