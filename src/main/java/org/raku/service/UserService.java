package org.raku.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.raku.dto.UserDetailsDto;
import org.raku.model.User;
import org.raku.repository.UserRepository;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepo;

    @Transactional
    public User userRegistartion(@Valid UserDetailsDto userDetails) {

        User user = User.builder()
                .userName(userDetails.getUserName())
                .email(userDetails.getEmail())
                .password(userDetails.getPassword())
                .mobileNum(userDetails.getMobileNum())
                .build();

        userRepo.persist(user);

        return user;
    }
}
