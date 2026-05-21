package org.raku.dto;


import jakarta.ws.rs.core.Response;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponseDto<T> {

    private LocalDateTime timestamp;

    private Response.Status status;

    private String error;

    private String message;

    private T data;
}