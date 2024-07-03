CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    card_number VARCHAR(255) NOT NULL
);

CREATE TABLE webhooks (
    url VARCHAR(2000) PRIMARY KEY
);

CREATE TABLE pending_notifications (
    id SERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    payment_id BIGINT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    CONSTRAINT fk_payment
      FOREIGN KEY(payment_id)
      REFERENCES payments(id)
);

CREATE TABLE distributed_lock (
    lock_name VARCHAR(255) PRIMARY KEY,
    locked_at TIMESTAMPTZ DEFAULT NOW()
);