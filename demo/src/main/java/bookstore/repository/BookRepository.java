package bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import bookstore.model.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findById(Integer bookId);
    Optional<Book> findByTitle(String title);
    Boolean existsByTitleAndAuthor(String title, String author);
}
