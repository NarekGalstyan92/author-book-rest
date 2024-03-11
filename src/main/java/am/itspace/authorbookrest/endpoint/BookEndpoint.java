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

    /**
     * Creates a new book by saving the provided SaveBookDto into the database.
     *
     * @param saveBookDto The SaveBookDto object that contains the details of the book to be created.
     * @return The BookResponseDto object representing the newly created book.
     */
    @PostMapping()
    public BookResponseDto create(@RequestBody SaveBookDto saveBookDto) {
        return bookService.save(saveBookDto);
    }


    /**
     * Retrieves all books from the system.
     *
     * @return A list of BookResponseDto objects representing the books.
     */
    @GetMapping
    public List<BookResponseDto> getAllBooks() {
        return bookService.getAllBooks();
    }


    /**
     * Retrieves a book from the database based on the given id.
     *
     * @param id The id of the book to retrieve.
     * @return A ResponseEntity containing the BookResponseDto if the book exists, otherwise returns a not found status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable("id") int id) {
        BookResponseDto bookFromDB = bookService.getBookById(id);
        if (bookFromDB == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookFromDB);
    }


    /**
     * Updates a book with the provided ID using the information from the SaveBookDto object.
     *
     * @param id          The ID of the book to be updated.
     * @param saveBookDto The DTO object containing the updated book information.
     * @return The ResponseEntity object with the updated BookResponseDto.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable("id") int id, @RequestBody SaveBookDto saveBookDto) {
        BookResponseDto bookById = bookService.getBookById(id);
        if (bookById == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(bookService.updateBook(id, saveBookDto));
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id The ID of the book to be deleted.
     * @return ResponseEntity with no content if the book was successfully deleted,
     * ResponseEntity with not found status if the book was not found.
     */
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
