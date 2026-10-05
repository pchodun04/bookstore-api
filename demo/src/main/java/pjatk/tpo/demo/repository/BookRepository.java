package pjatk.tpo.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pjatk.tpo.demo.model.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findById(Integer bookId);
    Optional<Book> findByTitle(String title);
}
