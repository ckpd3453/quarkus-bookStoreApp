package org.raku.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.raku.model.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
}
