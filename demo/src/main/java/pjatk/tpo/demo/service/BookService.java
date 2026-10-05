package pjatk.tpo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pjatk.tpo.demo.model.Book;
import pjatk.tpo.demo.repository.BookRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getBooks(String sortBy) {
        List<Book> books = bookRepository.findAll();

        switch (sortBy) {
            case "title" -> books.sort(Comparator.comparing(Book::getTitle));
            case "author" -> books.sort(Comparator.comparing(Book::getAuthor));
            case "price" -> books.sort(Comparator.comparingDouble(Book::getPrice));
        }

        return books;
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
