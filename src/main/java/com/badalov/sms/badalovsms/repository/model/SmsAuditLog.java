package com.badalov.sms.badalovsms.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sms_audit_log")
public class SmsAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "unifonic_message_id")
    private String unifonicMessageId;

    @Column(name = "t2_message_id")
    private String t2MessageId;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "status")
    private String status;

    @Column(name = "correlation_id")
    private String correlationId;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "receiver_phone_number")
    private String receiverPhoneNumber;

    @Column(name = "timestamp")
    private Instant timestamp;
}
