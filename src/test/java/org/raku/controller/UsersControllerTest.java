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
    void testForUserRegistration(){
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
    }

//    @Test
//    void testForUserLogin(){
//
//        UserDetailsDto request = UserDetailsDto.builder()
//                .email("chandrakantprasad68@gmail.com")
//                .password("Password@123")
//                .role("Admin")
//                .build();
//
//        ResponseDto.builder()
//                .timestamp(LocalDateTime.now())
//                .status(jakarta.ws.rs.core.Response.Status.ACCEPTED)
//                .message("User Login Success as Admin")
//                .data(token).build();
//    }
}
