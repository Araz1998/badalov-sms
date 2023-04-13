package com.badalov.sms.badalovsms.repository;

import com.badalov.sms.badalovsms.repository.model.SmsAuditLog;
import org.springframework.data.repository.CrudRepository;

public interface SmsAuditLogRepository extends CrudRepository<SmsAuditLog, Long> {

}
