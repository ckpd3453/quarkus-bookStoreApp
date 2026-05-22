package org.raku.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import io.quarkus.security.UnauthorizedException;
import org.raku.dto.ResponseDto;

import java.time.LocalDateTime;

@Provider
public class UnauthorizedExceptionMapper
        implements ExceptionMapper<UnauthorizedException> {

    @Override
    public Response toResponse(UnauthorizedException exception) {

        ResponseDto error = ResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .message("Authentication required. Please login.")
                .build();

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(error)
                .build();
    }
}
