package am.itspace.authorbookrest.repository;

import am.itspace.authorbookrest.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {


}
