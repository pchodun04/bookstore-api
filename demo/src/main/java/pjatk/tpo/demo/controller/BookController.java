package pjatk.tpo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pjatk.tpo.demo.model.Book;
import pjatk.tpo.demo.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/data")
@CrossOrigin
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getBooks(@RequestParam(defaultValue = "title") String sortBy) {
        return bookService.getBooks(sortBy);
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Integer id, @RequestParam String title, @RequestParam String author, @RequestParam int pages, @RequestParam double price) {
        return bookService.updateBook(id, title, author, pages, price);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
    }
}
