package com.pavanelli.payments.repository;

import com.pavanelli.payments.domain.PendingNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PendingNotificationRepository extends JpaRepository<PendingNotification, Long> {

    List<PendingNotification> findByRetriesLessThan(int limit);

}
