package am.itspace.authorbookrest.endpoint;

import am.itspace.authorbookrest.dto.BookResponseDto;
import am.itspace.authorbookrest.dto.SaveBookDto;
import am.itspace.authorbookrest.service.BookService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping()
    public BookResponseDto create(@RequestBody SaveBookDto saveBookDto) {
        return bookService.save(saveBookDto);
    }

    @GetMapping
    public List<BookResponseDto> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable("id") int id) {
        BookResponseDto bookFromDB = bookService.getBookById(id);
        if (bookFromDB == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookFromDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable("id") int id, @RequestBody SaveBookDto saveBookDto) {
        BookResponseDto bookById = bookService.getBookById(id);
        if (bookById == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(bookService.updateBook(id, saveBookDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookResponseDto> deleteBook(@PathVariable("id") int id) {
        BookResponseDto bookFromDB = bookService.getBookById(id);
        if (bookFromDB == null) {
            return ResponseEntity.notFound().build();
        }
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

}
