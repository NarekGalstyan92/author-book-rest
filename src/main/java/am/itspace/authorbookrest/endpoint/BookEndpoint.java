package am.itspace.authorbookrest.endpoint;

import am.itspace.authorbookrest.entity.Author;
import am.itspace.authorbookrest.entity.Book;
import am.itspace.authorbookrest.service.AuthorService;
import am.itspace.authorbookrest.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class represents the BookEndpoint which is
 * responsible for handling requests related to books.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/books")
public class BookEndpoint {

    private final BookService bookService;

    private final AuthorService authorService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") int id) {
        Book bookFromDB = bookService.getBookById(id);
        if (bookFromDB == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookFromDB);
    }

    @PostMapping("/{authorId}")
    public ResponseEntity<Book> addBook(@RequestBody Book book, @PathVariable("authorId") int authorId) {
        Author author = authorService.getById(authorId);
        book.setAuthor(author);
        bookService.addBook(book);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable("id") int id) {
        Book bookById = bookService.getBookById(id);
        if (bookById == null) {
            return ResponseEntity.notFound().build();
        }
        Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") int id) {
        Book bookFromDB = bookService.getBookById(id);
        if (bookFromDB == null) {
            return ResponseEntity.notFound().build();
        }
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

}
