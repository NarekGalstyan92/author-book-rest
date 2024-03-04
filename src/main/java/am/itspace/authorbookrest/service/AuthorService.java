package am.itspace.authorbookrest.service;

import am.itspace.authorbookrest.entity.Author;

import java.util.List;

public interface AuthorService {
    Author create(Author author);

    List<Author> getAll();

    Author getById(int id);

    Author update(int id, Author author);

    void deleteById(int id);
}
