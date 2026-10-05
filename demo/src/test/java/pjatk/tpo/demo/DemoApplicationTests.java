package pjatk.tpo.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pjatk.tpo.demo.model.Book;
import pjatk.tpo.demo.model.CartItem;
import pjatk.tpo.demo.model.User;
import pjatk.tpo.demo.repository.BookRepository;
import pjatk.tpo.demo.repository.CartItemRepository;
import pjatk.tpo.demo.repository.UserRepository;
import pjatk.tpo.demo.service.BookService;
import pjatk.tpo.demo.service.CartService;
import pjatk.tpo.demo.service.UserService;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DemoApplicationTests {

    @Mock
    BookRepository bookRepository;
    @Mock
    CartItemRepository cartItemRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    CartService cartService;
    @InjectMocks
    BookService bookService;
    @InjectMocks
    UserService userService;

    @Test
    void addNewBookToCart() {
        User user = new User(1, "Jan", "jan@test.pl", "haslo");
        Book book = new Book(1, "ksiazka", "Jan", 100, 50.00);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(cartItemRepository.save(any())).thenReturn(new CartItem(1, user, book, 2));

        CartItem cart = cartService.addBookToCart(1, 1, 2);
        assertThat(cart.getQuantity()).isEqualTo(2);
    }

    @Test
    void addExistingBookToCart() {
        User user = new User(1, "Jan", "jan@test.pl", "haslo");
        Book book = new Book(1, "ksiazka", "Jan", 100, 50.00);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(cartItemRepository.findCartItemByUserIdAndBookId(1,1)).thenReturn(Optional.of(new CartItem(1, user, book, 2)));
        cartService.addBookToCart(1, 1, 3);
        ArgumentCaptor<CartItem> captor = ArgumentCaptor.forClass(CartItem.class);
        verify(cartItemRepository).save(captor.capture());
        CartItem savedItem = captor.getValue();
        assertThat(savedItem.getQuantity()).isEqualTo(5);
    }

    @Test
    void deleteFromCart() {
        User user = new User(1, "Jan", "jan@test.pl", "haslo");
        Book book = new Book(1, "ksiazka", "Jan", 100, 50.00);
        CartItem cart = new CartItem(1, user, book, 2);
        when(cartItemRepository.findCartItemByUserIdAndBookId(1,1)).thenReturn(Optional.of(cart));
        cartService.removeBookFromCart(1, 1, 1);
        assertThat(cart.getQuantity()).isEqualTo(1);
        //czy dziala if
        cartService.removeBookFromCart(1, 1, 2);
        verify(cartItemRepository).delete(any());
    }

    @Test
    void addNewUser(){
        User user = new User(1, "Jan", "jan@test.pl", "haslo");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(new User(1, "Jan", "jan@test.pl", "haslo"));
        userService.saveUser(user);
        verify(userRepository).save(any());
    }

    @Test
    void addExistingUser(){
        User user = new User(1, "Jan", "jan@test.pl", "haslo");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        assertThrows(ApiException.class, () -> userService.saveUser(user));
    }

    @Test
    void updateUser(){
        User user = new User(1, "Jan", "jan@test.pl", "haslo");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        userService.updateUser(1, "Kuba");
        assertThat(user.getName()).isEqualTo("Kuba");
    }

    @Test
    void deleteUser(){
        User user = new User(1, "Jan", "jan@test.pl", "haslo");
        userService.deleteUser(user.getId());
        verify(userRepository).deleteById(1);
    }

    @Test
    void changePassword(){
        User user = new User(1, "Jan", "jan@test.pl", "haslo");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.changePassword(1, "haslo", "123");
        assertThat(user.getPassword()).isEqualTo("123");
    }

    @Test
    void loginTest(){
        User user = new User(1, "Jan", "jan@test.pl", "haslo");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        User result = userService.login(user.getEmail(), "haslo");
        assertThat(result.getEmail()).isEqualTo("jan@test.pl");
    }

    @Test
    void loginBadPassword(){
        User user = new User(1, "Jan", "jan@test.pl", "haslo");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        assertThrows(ApiException.class, () -> userService.login("jan@test.pl", "zlehaslo"));
    }

    @Test
    void loginWithBadEmail(){
        User user = new User(1, "Jan", "jan@test.pl", "haslo");
        assertThrows(ApiException.class, () -> userService.login("test@test.pl", user.getPassword()));
    }

    @Test
    void getNotExistingUser(){
        User user = new User(1, "Jan", "jan@test.pl", "haslo");
        assertThrows(ApiException.class, () -> userService.getUser(2));
    }

    @Test
    void updateBook(){
        Book book = new Book(1, "ksiazka", "Jan", 100, 50.00);
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        bookService.updateBook(1, "book", "Michal", 1, 100.00);
        assertThat(book.getTitle()).isEqualTo("book");
        assertThat(book.getAuthor()).isEqualTo("Michal");
        assertThat(book.getPrice()).isEqualTo(100.00);
    }

}
