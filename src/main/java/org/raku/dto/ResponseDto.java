package org.raku.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponseDto<T> {

    private LocalDateTime timestamp;

    private jakarta.ws.rs.core.Response.Status status;

    private String error;

    private String message;

    private T data;
}