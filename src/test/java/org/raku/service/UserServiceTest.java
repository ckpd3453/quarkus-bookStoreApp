package org.raku.service;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.raku.dto.ResponseDto;
import org.raku.dto.UserDetailsDto;
import org.raku.model.mysql.User;
import org.raku.repository.UserRepository;
import org.raku.utility.JwtService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class UserServiceTest {

    @Inject
    UserService userService;

    @InjectMock
    UserRepository userRepository;

    @InjectMock
    JwtService jwtService;

    @InjectMock
    AuditLogService auditLogService;

    @Test
    void shouldRegisterUserSuccessfully() {

        UserDetailsDto request = UserDetailsDto.builder()
                .userName("Chandrakant")
                .email("chandrakant@gmail.com")
                .mobileNum("7720154486")
                .password("Password@123")
                .role("Admin")
                .build();

        when(userRepository.findByEmailOrMobileNum(
                request.getEmail(),
                request.getMobileNum()))
                .thenReturn(null);

        ResponseDto response = userService.userRegistartion(request);

        assertEquals("User Registered Successfully!",
                response.getMessage());

        verify(userRepository, times(1))
                .persist(any(User.class));

        verify(auditLogService, times(1))
                .saveAudit(anyString());
    }


    @Test
    void shouldReturnErrorForExistingUser() {

        User existingUser = User.builder()
                .email("chandrakant@gmail.com")
                .mobileNum("7720154486")
                .build();

        when(userRepository.findByEmailOrMobileNum(
                "chandrakant@gmail.com",
                "7720154486"))
                .thenReturn(existingUser);

        UserDetailsDto request = UserDetailsDto.builder()
                .userName("Chandrakant")
                .email("chandrakant@gmail.com")
                .mobileNum("7720154486")
                .password("Password@123")
                .role("Admin")
                .build();

        ResponseDto response = userService.userRegistartion(request);

        assertEquals(
                "User Already Registered with this email or mobile number",
                response.getError());

        verify(userRepository, never())
                .persist(any(User.class));
    }


    @Test
    void shouldLoginSuccessfully() {

        String password = "Password@123";

        User user = User.builder()
                .email("chandrakant@gmail.com")
                .password(
                        io.quarkus.elytron.security.common.BcryptUtil
                                .bcryptHash(password))
                .role("Admin")
                .build();

        when(userRepository.findByEmailOrMobileNum(
                "chandrakant@gmail.com",
                null))
                .thenReturn(user);

        when(jwtService.generateToken(
                user.getEmail(),
                user.getRole()))
                .thenReturn("dummy-jwt-token");

        UserDetailsDto request = UserDetailsDto.builder()
                .email("chandrakant@gmail.com")
                .password(password)
                .build();

        ResponseDto response = userService.userLogin(request);

        assertEquals(
                "User Login Success as Admin",
                response.getMessage());

        assertEquals(
                "dummy-jwt-token",
                response.getData());

        verify(jwtService, times(1))
                .generateToken(anyString(), anyString());
    }


    @Test
    void shouldReturnUserNotFound() {

        when(userRepository.findByEmailOrMobileNum(
                "unknown@gmail.com",
                null))
                .thenReturn(null);

        UserDetailsDto request = UserDetailsDto.builder()
                .email("unknown@gmail.com")
                .password("Password@123")
                .build();

        ResponseDto response = userService.userLogin(request);

        assertEquals(
                "User Not Found/Unregistered",
                response.getError());

        verify(jwtService, never())
                .generateToken(anyString(), anyString());
    }


    @Test
    void shouldReturnWrongPassword() {

        User user = User.builder()
                .email("chandrakant@gmail.com")
                .password(
                        io.quarkus.elytron.security.common.BcryptUtil
                                .bcryptHash("CorrectPassword"))
                .role("Admin")
                .build();

        when(userRepository.findByEmailOrMobileNum(
                "chandrakant@gmail.com",
                null))
                .thenReturn(user);

        UserDetailsDto request = UserDetailsDto.builder()
                .email("chandrakant@gmail.com")
                .password("WrongPassword")
                .build();

        ResponseDto response = userService.userLogin(request);

        assertEquals(
                "User Password is not correct",
                response.getError());

        verify(jwtService, never())
                .generateToken(anyString(), anyString());
    }
}