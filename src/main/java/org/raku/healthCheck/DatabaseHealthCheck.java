package org.raku.healthCheck;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import jakarta.enterprise.context.ApplicationScoped;

@Readiness
@ApplicationScoped
public class DatabaseHealthCheck implements HealthCheck {

    @Inject
    EntityManager entityManager;

    @Override
    public HealthCheckResponse call() {


        try {
            entityManager
                    .createNativeQuery("SELECT 1")
                    .getSingleResult();

            return HealthCheckResponse.up("MySQL");

        } catch (Exception e) {

            return HealthCheckResponse.down("MySQL");
        }
    }
}
