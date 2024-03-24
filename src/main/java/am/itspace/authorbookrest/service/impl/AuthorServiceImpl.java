package am.itspace.authorbookrest.service.impl;

import am.itspace.authorbookrest.dto.AuthorResponseDto;
import am.itspace.authorbookrest.dto.SaveAuthorDto;
import am.itspace.authorbookrest.entity.Author;
import am.itspace.authorbookrest.mapper.AuthorMapper;
import am.itspace.authorbookrest.repository.AuthorRepository;
import am.itspace.authorbookrest.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The AuthorServiceImpl class implements the AuthorService interface and provides
 * the implementation for all its methods.
 *
 * @version 1.0
 * @since 2024-03-10
 */
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    /**
     * Creates an Author object based on the provided SaveAuthorDto and saves it to the database.
     *
     * @param saveAuthorDto The SaveAuthorDto object containing the data for the new author.
     * @return An AuthorResponseDto object representing the saved author.
     */
    @Override
    public AuthorResponseDto create(SaveAuthorDto saveAuthorDto) {
        return authorMapper.map(authorRepository.save(authorMapper.map(saveAuthorDto)));
    }

    /**
     * Retrieves all authors from the author repository and maps them to AuthorResponseDto objects.
     *
     * @return a list of AuthorResponseDto objects representing all the authors
     */
    @Override
    public List<AuthorResponseDto> getAll(Pageable pageable) {
        Page<Author> all = authorRepository.findAll(pageable);
        List<AuthorResponseDto> authorResponseDtos = new ArrayList<>();
        for (Author author : all.getContent()) {
            authorResponseDtos.add(authorMapper.map(author));
        }
        return authorResponseDtos;
    }

    /**
     * Retrieves an AuthorResponseDto object by the provided id.
     *
     * @param id The id of the author to retrieve.
     * @return An AuthorResponseDto object representing the author with the provided id.
     * Returns null if no author is found with the provided id.
     */
    @Override
    public AuthorResponseDto getById(int id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return null;
        }
        return authorMapper.map(author);
    }

    /**
     * Updates an existing author in the database with the provided ID.
     *
     * @param id     The ID of the author to update.
     * @param author The SaveAuthorDto object containing the updated data for the author.
     * @return An AuthorResponseDto object representing the updated author.
     */
    @Override
    public AuthorResponseDto update(int id, SaveAuthorDto author) {

        Author savedAuthor = authorMapper.map(author);
        savedAuthor.setId(id);
        authorRepository.save(savedAuthor);

        return authorMapper.map(savedAuthor);
    }

    /**
     * Deletes an author from the author repository based on the provided id.
     *
     * @param id The id of the author to be deleted.
     */
    @Override
    public void deleteById(int id) {
        authorRepository.deleteById(id);
    }
}
