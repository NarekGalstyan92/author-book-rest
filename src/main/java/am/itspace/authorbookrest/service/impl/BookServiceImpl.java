package am.itspace.authorbookrest.service.impl;

import am.itspace.authorbookrest.entity.Book;
import am.itspace.authorbookrest.repository.BookRepository;
import am.itspace.authorbookrest.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(int id, Book book) {
        Book existingBook = bookRepository.findById(id).orElse(null);
        if (existingBook != null) {
            book.setId(existingBook.getId());
            return bookRepository.save(book);
        } else {
            return null;
        }
    }

    @Override
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }
}
