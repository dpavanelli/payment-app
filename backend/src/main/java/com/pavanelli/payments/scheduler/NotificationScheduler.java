package com.pavanelli.payments.scheduler;

import com.pavanelli.payments.service.LockService;
import com.pavanelli.payments.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private LockService lockService;

    private static final String LOCK_NAME = "notification_retry_lock";

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
