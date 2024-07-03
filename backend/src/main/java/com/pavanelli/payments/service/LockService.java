package com.pavanelli.payments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * This service provides a simple locking mechanism using a database table.
 * It is used to ensure that certain operations are not performed concurrently.
 */
@Service
public class LockService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Attempts to acquire a lock with the specified name.
     * If the lock already exists, it returns false, indicating that the lock could not be acquired.
     * This method uses a transaction with SERIALIZABLE isolation level to ensure the lock is acquired safely.
     *
     * @param lockName the name of the lock to acquire
     * @return true if the lock was successfully acquired, false if the lock already exists
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean acquireLock(String lockName) {
        try {
            String sql = "INSERT INTO distributed_lock (lock_name, locked_at) VALUES (?, ?)";
            jdbcTemplate.update(sql, lockName, LocalDateTime.now());
            return true;
        } catch (DuplicateKeyException e) {
            return false;
        }
    }

    /**
     * Releases the lock with the specified name.
     * This allows other processes to acquire the lock.
     * This method uses a transaction with SERIALIZABLE isolation level to ensure the lock is released safely.
     *
     * @param lockName the name of the lock to release
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void releaseLock(String lockName) {
        String sql = "DELETE FROM distributed_lock WHERE lock_name = ?";
        jdbcTemplate.update(sql, lockName);
    }
}
