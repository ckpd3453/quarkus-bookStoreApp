package org.raku.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.raku.model.mysql.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User findByEmailOrMobileNum(String email, String mobileNum) {
        return find("email = ?1 or mobileNum = ?2", email, mobileNum)
                .firstResult();
    }


}
