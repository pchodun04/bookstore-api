package pjatk.tpo.demo.service;

import org.springframework.stereotype.Service;
import pjatk.tpo.demo.ApiException;
import pjatk.tpo.demo.model.Book;
import pjatk.tpo.demo.model.CartItem;
import pjatk.tpo.demo.model.User;
import pjatk.tpo.demo.repository.BookRepository;
import pjatk.tpo.demo.repository.CartItemRepository;
import pjatk.tpo.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public CartService(CartItemRepository cartItemRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public CartItem addBookToCart(Integer userId, Integer bookId, Integer quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> ApiException.notFound("Uzytkowik nie istnieje"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> ApiException.notFound("Ksiazka nie istnieje"));
        Optional<CartItem> cartItem = cartItemRepository.findCartItemByUserIdAndBookId(userId, bookId);
        if(cartItem.isPresent()) {
            CartItem oldCartItem = cartItem.get();
            oldCartItem.setQuantity(quantity + oldCartItem.getQuantity());
            return cartItemRepository.save(oldCartItem);
        }else{
            CartItem newCartItem = new CartItem();
            newCartItem.setUser(user);
            newCartItem.setBook(book);
            newCartItem.setQuantity(quantity);
            return cartItemRepository.save(newCartItem);
        }
    }

    public CartItem removeBookFromCart(Integer userId, Integer bookId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findCartItemByUserIdAndBookId(userId, bookId).orElseThrow(() -> ApiException.notFound("Koszyk nie istnieje"));
        int newBookQuantity = cartItem.getQuantity() - quantity;
        if(newBookQuantity <= 0) {
            cartItemRepository.delete(cartItem);
            return null;
        }else{
            cartItem.setQuantity(newBookQuantity);
            return cartItemRepository.save(cartItem);
        }
    }

    public CartItem getCartItem(Integer userId, Integer bookId) {
        return cartItemRepository.findCartItemByUserIdAndBookId(userId, bookId).orElseThrow(() -> ApiException.notFound("Nie istnieje"));
    }

    public List<CartItem> getCartItems(Integer userId) {
        return cartItemRepository.findCartItemsByUserId(userId);
    }
}
