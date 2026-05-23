package org.raku.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.raku.dto.BookDto;
import org.raku.dto.ResponseDto;
import org.raku.model.mysql.Book;
import org.raku.repository.BookRepository;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class BookService {

    @Inject
    BookRepository bookRepository;

    @Inject
    AuditLogService auditLogService;

    @Transactional
    public ResponseDto addBook(BookDto bookDetails) {

        String normalizedTitle = normalize(bookDetails.getTitle());
        String normalizedAuthor = normalize(bookDetails.getAuthor());

        Book existingBook =
                bookRepository.findByTitleAndAuthor(
                        normalizedTitle,
                        normalizedAuthor
                );

        if (existingBook != null) {

            existingBook.setStock(
                    existingBook.getStock() + bookDetails.getStock()
            );

            auditLogService.saveAudit(bookDetails.getTitle()+" already exists. Stock updated successfully.");
            return ResponseDto.builder()
                    .timestamp(LocalDateTime.now())
                    .status(Response.Status.OK)
                    .message("Book already exists. Stock updated successfully.")
                    .data(existingBook)
                    .build();
        }

        Book book = Book.builder()
                .title(normalizedTitle)
                .author(normalizedAuthor)
                .category(bookDetails.getCategory())
                .price(bookDetails.getPrice())
                .stock(bookDetails.getStock())
                .description(bookDetails.getDescription())
                .build();

        bookRepository.persist(book);

        auditLogService.saveAudit(bookDetails.getTitle()+" added successfully.");
        return ResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(Response.Status.CREATED)
                .message("Book added successfully.")
                .data(book)
                .build();
    }

    private String normalize(String value) {
        return value == null
                ? null
                : value.trim()
                .replaceAll("\\s+", " ")
                .toUpperCase();
    }

    public ResponseDto getAllBooks() {

        List<Book> list = bookRepository.findAll().list();

        auditLogService.saveAudit("Books fetched Successfully!");
        return ResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(Response.Status.OK)
                .data(list)
                .message("Books fetched Successfully!")
                .build();
    }

    @Transactional
    public ResponseDto removeBook(Long id) {

        boolean deleted = bookRepository.deleteById(id);

        return deleted ? ResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(Response.Status.ACCEPTED)
                .message("Book removed from inventory Successfully!")
                .data(deleted)
                .build() :
                ResponseDto.builder()
                        .timestamp(LocalDateTime.now())
                        .status(Response.Status.CONFLICT)
                        .error("Something went wrong or the book which you want to delete is not existing")
                        .data(deleted)
                        .build();
    }
}
