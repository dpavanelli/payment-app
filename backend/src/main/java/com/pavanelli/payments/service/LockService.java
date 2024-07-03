package com.pavanelli.payments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LockService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean acquireLock(String lockName) {
        try {
            String sql = "INSERT INTO distributed_lock (lock_name, locked_at) VALUES (?, ?)";
            jdbcTemplate.update(sql, lockName, LocalDateTime.now());
            return true;
        } catch (DuplicateKeyException e) {
            return false;
        }
    }

    public void releaseLock(String lockName) {
        String sql = "DELETE FROM distributed_lock WHERE lock_name = ?";
        jdbcTemplate.update(sql, lockName);
    }
}
