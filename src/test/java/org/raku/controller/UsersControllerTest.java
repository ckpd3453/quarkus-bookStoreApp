package org.raku.controller;


import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.raku.dto.ResponseDto;
import org.raku.dto.UserDetailsDto;
import org.raku.service.UserService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@QuarkusTest
public class UsersControllerTest {


    @Inject
    UsersController usersController;

    @InjectMock
    UserService userService;

    @Test
    void testForUserRegistration() {
        UserDetailsDto user = UserDetailsDto.builder()
                .userName("Chandrakant")
                .email("chandrakantprasad68@gmail.com")
                .mobileNum("7720153486")
                .role("Admin")
                .password("Password@123")
                .build();


        ResponseDto<Object> responseDto = ResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(Response.Status.CREATED)
                .data(user)
                .message("User Registered Successfully!")
                .build();

        when(userService.userRegistartion(Mockito.any())).thenReturn(responseDto);

        var response = usersController.userRegistration(user);

        assertEquals(200, response.getStatus());

        ResponseDto actualResponsse = (ResponseDto) response.getEntity();

        assertEquals("User Registered Successfully!", actualResponsse.getMessage());
        assertEquals(responseDto, actualResponsse);
    }

    @Test
    void testForUserLogin() {

        UserDetailsDto request = UserDetailsDto.builder()
                .email("chandrakantprasad68@gmail.com")
                .password("Password@123")
                .role("Admin")
                .build();

        String token = "dummy-jwt-token";

        ResponseDto<Object> responseDto = ResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(Response.Status.ACCEPTED)
                .message("User Login Success as Admin")
                .data(token)
                .build();

        when(userService.userLogin(Mockito.any()))
                .thenReturn(responseDto);

        var response = usersController.userLogin(request);

        assertEquals(200, response.getStatus());

        ResponseDto actualResponse =
                (ResponseDto) response.getEntity();

        assertEquals("User Login Success as Admin",
                actualResponse.getMessage());

        assertEquals(token,
                actualResponse.getData());
    }
}
