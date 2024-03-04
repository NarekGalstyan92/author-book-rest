package am.itspace.authorbookrest.service;

import am.itspace.authorbookrest.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book getBookById(int id);

    Book addBook(Book book);

    Book updateBook(int id, Book book);

    void deleteBook(int id);
}
