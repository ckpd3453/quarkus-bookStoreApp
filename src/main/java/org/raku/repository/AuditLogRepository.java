package org.raku.repository;

import io.quarkus.hibernate.orm.PersistenceUnit;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.raku.model.h2db.AuditLog;

@ApplicationScoped
public class AuditLogRepository implements PanacheRepository<AuditLog> {
}
