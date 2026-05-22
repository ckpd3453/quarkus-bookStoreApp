package org.raku.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.raku.dto.BookDto;
import org.raku.dto.ResponseDto;
import org.raku.service.BookService;

@Path("bookstore/book")
public class BooksController {

    @Inject
    BookService bookService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    public Response addBook(BookDto bookDetails){

        ResponseDto response = bookService.addBook(bookDetails);
        return Response.ok(response).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getAllBooks(){
        ResponseDto allBooks = bookService.getAllBooks();

        return Response.ok(allBooks).build();
    }

    @DELETE
    @Path("{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed("Admin")
    public Response removeBook(@PathParam("bookId") Long id){

        ResponseDto responseDto = bookService.removeBook(id);
        return Response.ok(responseDto).build();
    }
}
