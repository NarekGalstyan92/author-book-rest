package am.itspace.authorbookrest.service.impl;

import am.itspace.authorbookrest.dto.BookResponseDto;
import am.itspace.authorbookrest.dto.CBCurrencyResponseDto;
import am.itspace.authorbookrest.dto.SaveBookDto;
import am.itspace.authorbookrest.entity.Book;
import am.itspace.authorbookrest.mapper.BookMapper;
import am.itspace.authorbookrest.repository.AuthorRepository;
import am.itspace.authorbookrest.repository.BookRepository;
import am.itspace.authorbookrest.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    private final RestTemplate restTemplate;

    @Value("${cb.armUrl}")
    private String cbArmUrl;


    @Override
    public BookResponseDto save(SaveBookDto saveBookDto) {
        Book book = bookMapper.map(saveBookDto);
        book.setAuthor(authorRepository.findById(saveBookDto.getAuthorId()).orElse(null));
        bookRepository.save(book);
        return bookMapper.map(book);
    }


    @Override
    public BookResponseDto getBookById(int id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return null;
        }
        return bookMapper.map(book);
    }

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

    @Override
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookResponseDto> getAll() {
        List<Book> all = bookRepository.findAll(); // Fetching all the books from the book repository
        List<BookResponseDto> dtoList = new ArrayList<>(); // Creating an empty list to hold BookResponseDto objects

        if (!all.isEmpty()) { // Checking if the list of fetched books is not empty
            double usdCurrency = getUsdCurrency(); // Retreiving the current USD currency rate

            // Looping over all the fetched books
            for (Book book : all) {
                BookResponseDto bookResponseDto = bookMapper.map(book); // Mapping the book object to a BookResponseDto object
                setUsdPrice(bookResponseDto, usdCurrency); // Setting the USD price for the book
                dtoList.add(bookResponseDto);    // Adding the BookResponseDto object to the list
            }
        }
        return dtoList; // Returning the list of BookResponseDto objects
    }

    private void setUsdPrice(BookResponseDto bookResponseDto, double usdCurrency) {
        bookResponseDto.setPriceUSD(bookResponseDto.getPrice() / usdCurrency); // Sets the price in USD by dividing the original price by the exchange rate
    }

    private double getUsdCurrency() {

        ResponseEntity<CBCurrencyResponseDto> responseEntity = restTemplate.getForEntity(cbArmUrl, CBCurrencyResponseDto.class); // Perform a GET request for the exchange rate

        // Check if the request was successful
        if (responseEntity.getStatusCode() == HttpStatusCode.valueOf(200)) {
            CBCurrencyResponseDto body = responseEntity.getBody(); // If the request was successful, parse the exchange rate from the response
            double parsed = Double.parseDouble(body.getUsd()); // Converts the usd value from String to double
            return Math.round(parsed); // Round the exchange rate to the nearest whole number and return it
        }
        return 0; // If the request failed, return a default value of 0
    }
}
