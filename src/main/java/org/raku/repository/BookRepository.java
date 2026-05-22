package org.raku.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.raku.model.Book;

@ApplicationScoped
public class BookRepository implements PanacheRepository<Book> {


    public Book findByTitleAndAuthor(String title, String author) {

        return find(
                "title = ?1 and author = ?2",
                title,
                author
        ).firstResult();
    }
}
