package com.pavanelli.payments.repository;

import com.pavanelli.payments.domain.Webhook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebhookRepository extends JpaRepository<Webhook, String> {

    boolean existsByUrl(String url);

}