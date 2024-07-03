package com.pavanelli.payments.scheduler;

import com.pavanelli.payments.service.LockService;
import com.pavanelli.payments.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * This component handles the retry mechanism for previously failed notifications.
 * It uses a lock to ensure that only one instance of the retry process runs at a time.
 */
@Component
public class NotificationScheduler {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private LockService lockService;

    private static final String LOCK_NAME = "notification_retry_lock";

    /**
     * Scheduled task that runs every minute to retry pending notifications.
     * It acquires a lock before processing to prevent concurrent executions.
     */
    @Scheduled(fixedRate = 60000) // Runs every minute
    public void retryPendingNotifications() {
        if (lockService.acquireLock(LOCK_NAME)) {
            try {
                notificationService.retryPendingNotifications();
            } finally {
                lockService.releaseLock(LOCK_NAME);
            }
        }
    }
}
