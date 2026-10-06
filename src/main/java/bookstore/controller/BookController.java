package bookstore.controller;

import org.springframework.web.bind.annotation.*;
import bookstore.dto.dto;
import bookstore.model.Book;
import bookstore.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/book")
@CrossOrigin
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks(@RequestParam(defaultValue = "title") String sortBy) {
        return bookService.getBooks(sortBy);
    }

    @GetMapping("/{title}")
    public dto.Book getBook(@PathVariable String title) {
        Book book = bookService.getBook(title);
        return new dto.Book(book.getTitle(), book.getAuthor(), book.getPages(), book.getPrice());
    }

    @PostMapping
    public dto.Book addBook(@RequestBody dto.Book book) {
        Book savedBook = new Book();
        savedBook.setTitle(book.title());
        savedBook.setAuthor(book.author());
        savedBook.setPages(book.pages());
        savedBook.setPrice(book.price());
        Book newBook = bookService.saveBook(savedBook);
        return new dto.Book(newBook.getTitle(), newBook.getAuthor(), newBook.getPages(), newBook.getPrice());
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Integer id, @RequestBody dto.Book book) {
        return bookService.updateBook(id, book.title(), book.author(), book.pages(), book.price());
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
    }
}
