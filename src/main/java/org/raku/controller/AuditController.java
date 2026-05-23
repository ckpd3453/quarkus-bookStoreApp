package org.raku.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.raku.dto.ResponseDto;
import org.raku.service.AuditLogService;

@Path("bookstore/audit")
public class AuditController {


    @Inject
    AuditLogService auditLogService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuditLogs(){
        ResponseDto responseDto = auditLogService.getAuditLogs();
        return Response.ok(responseDto).build();
    }
}
