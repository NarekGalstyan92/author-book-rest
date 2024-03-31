package am.itspace.authorbookrest.endpoint;

import am.itspace.authorbookrest.dto.BookFilterDto;
import am.itspace.authorbookrest.dto.BookResponseDto;
import am.itspace.authorbookrest.dto.SaveBookDto;
import am.itspace.authorbookrest.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public BookResponseDto create(@Valid @RequestBody SaveBookDto saveBookDto) {
//        if (saveBookDto.getTitle() == null) {
//            throw new IllegalArgumentException("title can't be null");
//        }
        return bookService.save(saveBookDto);
    }

    @GetMapping
    public List<BookResponseDto> getAll() {
        return bookService.getAll();
    }

    @PostMapping("/filter")
    public List<BookResponseDto> getAllByFilter(@RequestBody BookFilterDto bookFilterDto) {
        return bookService.getAllByFilter(bookFilterDto);
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
