package am.itspace.authorbookrest.service;

import am.itspace.authorbookrest.dto.BookResponseDto;
import am.itspace.authorbookrest.dto.SaveBookDto;

import java.util.List;

public interface BookService {

    BookResponseDto save(SaveBookDto saveBookDto);

    List<BookResponseDto> getAllBooks();

    BookResponseDto getBookById(int id);

    BookResponseDto updateBook(int id, SaveBookDto saveBookDto);

    void deleteBook(int id);

}
