package am.itspace.authorbookrest.service.impl;

import am.itspace.authorbookrest.dto.BookResponseDto;
import am.itspace.authorbookrest.dto.SaveBookDto;
import am.itspace.authorbookrest.entity.Book;
import am.itspace.authorbookrest.mapper.BookMapper;
import am.itspace.authorbookrest.repository.AuthorRepository;
import am.itspace.authorbookrest.repository.BookRepository;
import am.itspace.authorbookrest.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The BookServiceImpl class is responsible for implementing the BookService interface.
 * It provides methods to perform CRUD operations on books using the BookRepository, AuthorRepository, and BookMapper.
 *
 * @see BookService
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;


    /**
     * Saves a book to the database.
     *
     * @param saveBookDto The SaveBookDto object containing the book details to be saved.
     *                    It must not be null.
     * @return The BookResponseDto object representing the saved book.
     * It may be null if the book could not be saved.
     */
    @Override
    public BookResponseDto save(SaveBookDto saveBookDto) {
        Book book = bookMapper.map(saveBookDto);
        book.setAuthor(authorRepository.findById(saveBookDto.getAuthorId()).orElse(null));
        bookRepository.save(book);
        return bookMapper.map(book);
    }

    /**
     * Retrieves a list of all books.
     *
     * @return the list of all books as BookResponseDto objects
     */
    @Override
    public List<BookResponseDto> getAllBooks() {
        List<Book> all = bookRepository.findAll();
        List<BookResponseDto> bookResponseDtoList = new ArrayList<>();
        for (Book book : all) {
            bookResponseDtoList.add(bookMapper.map(book));
        }
        return bookResponseDtoList;
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id The ID of the book to retrieve. It must be an integer value.
     * @return The BookResponseDto object representing the retrieved book.
     * If the book with the given ID does not exist, null is returned.
     */
    @Override
    public BookResponseDto getBookById(int id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return null;
        }
        return bookMapper.map(book);
    }


    /**
     * Updates an existing book in the database based on the provided book ID and book details.
     *
     * @param id          The ID of the book to update. Must be a positive integer.
     * @param saveBookDto The SaveBookDto object containing the updated book details. Must not be null.
     * @return The BookResponseDto object representing the updated book. Returns null if the book with the given ID is not found.
     */
    @Override
    public BookResponseDto updateBook(int id, SaveBookDto saveBookDto) {
        // Find the existing book from bookRepository
        Book existingBook = bookRepository.findById(id).orElse(null);

        if (existingBook == null) {
            return null;
        }

        // Map saveBookDto to a new Book
        Book updatedBook = bookMapper.map(saveBookDto);

        // Set the same author of existingBook to updatedBook
        updatedBook.setAuthor(existingBook.getAuthor());

        // Set the id of existingBook to updatedBook
        updatedBook.setId(existingBook.getId());

        // Map saved updatedBook to BookResponseDto
        bookRepository.save(updatedBook);
        return bookMapper.map(updatedBook);
    }

    /**
     * Deletes a book from the database based on the given ID.
     *
     * @param id The ID of the book to be deleted. It must be a positive non-zero integer.
     */
    @Override
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }
}
