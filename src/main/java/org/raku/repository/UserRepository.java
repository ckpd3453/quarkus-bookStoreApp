package org.raku.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.Email;
import org.raku.model.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User findByEmailOrMobileNum(String email, String mobileNum) {
        return find("email = ?1 or mobileNum = ?2", email, mobileNum)
                .firstResult();
    }


}
