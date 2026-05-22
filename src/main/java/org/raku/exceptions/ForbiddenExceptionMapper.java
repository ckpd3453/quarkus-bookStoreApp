package org.raku.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import io.quarkus.security.ForbiddenException;
import org.raku.dto.ResponseDto;

import java.time.LocalDateTime;

@Provider
public class ForbiddenExceptionMapper
        implements ExceptionMapper<ForbiddenException> {

    @Override
    public Response toResponse(ForbiddenException exception) {

        ResponseDto error = ResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .message("Access denied. You do not have sufficient privileges.")
                .build();

        return Response.status(Response.Status.FORBIDDEN)
                .entity(error)
                .build();
    }
}
