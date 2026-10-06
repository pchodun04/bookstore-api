package bookstore.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import bookstore.ApiException;
import bookstore.model.Book;
import bookstore.repository.BookRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(Book book) {
        if(bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) {
            throw ApiException.invalidData("Taka ksiazka juz istnieje");
        }
        return bookRepository.save(book);
    }

    public List<Book> getBooks(String sortBy) {
        Sort sort = switch (sortBy){
            case "author" -> Sort.by("author");
            case "price" -> Sort.by("price");
            default -> Sort.by("title");
        };

        return bookRepository.findAll(sort);
    }

    public Book getBook(String title) {
        return bookRepository.findByTitle(title).orElseThrow(() -> ApiException.notFound("Taka ksiazka nie istnieje"));
    }

    public Book updateBook(Integer id, String title, String author, int pages, double price) {
        Book toUpdate = bookRepository.findById(id).orElse(null);
        if (toUpdate == null) return null;

        toUpdate.setTitle(title);
        toUpdate.setAuthor(author);
        toUpdate.setPages(pages);
        toUpdate.setPrice(price);

        return bookRepository.save(toUpdate);
    }

    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }
}
