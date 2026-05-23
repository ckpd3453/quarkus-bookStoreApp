package org.raku.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.raku.dto.ResponseDto;
import org.raku.model.h2db.AuditLog;
import org.raku.repository.AuditLogRepository;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class AuditLogService {

    @Inject
    AuditLogRepository auditLogRepository;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void saveAudit(String action) {

        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setCreatedAt(LocalDateTime.now());

        auditLogRepository.persist(log);
    }

    public ResponseDto getAuditLogs() {

        List<AuditLog> list = auditLogRepository.findAll().list();

        return ResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(Response.Status.OK)
                .data(list)
                .message("Logs Fetched Successfully from H2DB!")
                .build();
    }
}
