package am.itspace.authorbookrest.endpoint;

import am.itspace.authorbookrest.dto.AuthorResponseDto;
import am.itspace.authorbookrest.dto.SaveAuthorDto;
import am.itspace.authorbookrest.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * This class represents the AuthorEndpoint which is
 * responsible for handling requests related to authors.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/authors")
public class AuthorEndpoint {

    private final AuthorService authorService;

    /**
     * Creates a new author based on the given SaveAuthorDto.
     *
     * @param authorDto The SaveAuthorDto containing the information of the author to be created.
     * @return The AuthorResponseDto representing the created author.
     */
    @PostMapping
    public AuthorResponseDto createAuthor(@RequestBody SaveAuthorDto authorDto) {
        return authorService.create(authorDto);
    }

    /**
     * Retrieves a list of all authors.
     *
     * @return A List of AuthorResponseDto objects representing the authors.
     */
    @GetMapping
    public List<AuthorResponseDto> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                          @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                          @RequestParam(value = "orderBy", required = false, defaultValue = "id") String orderBy,
                                          @RequestParam(value = "order", required = false, defaultValue = "DESC") String order) {

        Sort sort = Sort.by(Sort.Direction.fromString(order), orderBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        return authorService.getAll(pageable);
    }

    /**
     * Retrieves an AuthorResponseDto by their ID.
     *
     * @param id The ID of the author.
     * @return The ResponseEntity containing the AuthorResponseDto if found,
     * or ResponseEntity.notFound() if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getById(@PathVariable("id") int id) {
        AuthorResponseDto author = authorService.getById(id);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(author);
    }

    /**
     * Updates an existing author with the given ID using the provided author information.
     *
     * @param id        The ID of the author to update.
     * @param authorDto The SaveAuthorDto containing the updated information of the author.
     * @return The ResponseEntity containing the AuthorResponseDto representing the updated author
     * if found, or ResponseEntity.notFound() if the author is not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> updateAuthor(@PathVariable("id") int id, @RequestBody SaveAuthorDto authorDto) {
        AuthorResponseDto byId = authorService.getById(id);
        if (byId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authorService.update(id, authorDto));
    }

    /**
     * Deletes an author by ID.
     *
     * @param id The ID of the author to be deleted.
     * @return The ResponseEntity representing the status of the deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> deleteAuthor(@PathVariable("id") int id) {
        AuthorResponseDto author = authorService.getById(id);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }
        authorService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
