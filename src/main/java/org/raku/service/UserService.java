package org.raku.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import org.raku.dto.ResponseDto;
import org.raku.dto.UserDetailsDto;
import org.raku.model.User;
import org.raku.repository.UserRepository;

import java.time.LocalDateTime;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepo;

    @Transactional
    public ResponseDto userRegistartion(@Valid UserDetailsDto userDetails) {

        User emailOrMobileNum = userRepo.findByEmailOrMobileNum(userDetails.getEmail(), userDetails.getMobileNum());

        if (emailOrMobileNum != null) {
            return ResponseDto.builder()
                    .timestamp(LocalDateTime.now())
                    .status(Response.Status.BAD_REQUEST)
                    .error("User Already Registered with this email or mobile number")
                    .data(emailOrMobileNum.getEmail())
                    .build();
        }

        String encrytedPassword = BcryptUtil.bcryptHash(userDetails.getPassword());
        User user = User.builder()
                .userName(userDetails.getUserName())
                .email(userDetails.getEmail())
                .password(encrytedPassword)
                .mobileNum(userDetails.getMobileNum())
                .build();

        userRepo.persist(user);

        return ResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(Response.Status.CREATED)
                .data(user)
                .message("User Registered Successfully!")
                .build();
    }
}
