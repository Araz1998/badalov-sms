CREATE TABLE sms_audit_log
(
    id                      BIGINT IDENTITY(100000, 1) PRIMARY KEY,
    unifonic_message_id     VARCHAR(225) NULL,
    t2_message_id           VARCHAR(255) NULL,
    error_message           VARCHAR(255) NULL,
    error_code              VARCHAR(255) NULL,
    status                  VARCHAR(255) NOT NULL,
    correlation_id          VARCHAR(255) NULL,
    sender_name             VARCHAR(255) NULL,
    receiver_phone_number   VARCHAR(255) NULL,
    timestamp               DATETIME    NULL
);