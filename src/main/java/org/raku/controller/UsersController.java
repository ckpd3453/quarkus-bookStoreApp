package org.raku.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.raku.dto.ResponseDto;
import org.raku.dto.UserDetailsDto;
import org.raku.service.UserService;

@Path("bookstore/user")
public class UsersController {

    @Inject
    UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userRegistration(@Valid UserDetailsDto userDetails){
        ResponseDto responseDto = userService.userRegistartion(userDetails);
        return Response.ok(responseDto).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response userLogin(@Valid UserDetailsDto userDetails){
        ResponseDto responseDto = userService.userLogin(userDetails);
        return Response.ok(responseDto).build();
    }
}
