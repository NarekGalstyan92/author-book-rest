package am.itspace.authorbookrest.repository;

import am.itspace.authorbookrest.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {


}
