package pjatk.tpo.demo.controller;

import org.springframework.web.bind.annotation.*;
import pjatk.tpo.demo.model.CartItem;
import pjatk.tpo.demo.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {

    CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public List<CartItem> getCartItem(@PathVariable Integer userId) {
        return cartService.getCartItems(userId);
    }

    @GetMapping("/{userId}/{bookId}")
    public CartItem getCartItem(@PathVariable Integer userId, @PathVariable Integer bookId) {
        return cartService.getCartItem(userId, bookId);
    }

    @PostMapping("/{userId}/add/{bookId}")
    public CartItem addCartItem(@PathVariable Integer userId, @PathVariable Integer bookId, @RequestParam(defaultValue = "1") Integer quantity) {
        return cartService.addBookToCart(userId, bookId, quantity);
    }

    @PostMapping("/{userId}/remove/{bookId}")
    public CartItem removeBookFromCart(@PathVariable Integer userId, @PathVariable Integer bookId, @RequestParam(defaultValue = "1") Integer quantity) {
        return cartService.removeBookFromCart(userId, bookId, quantity);
    }
}
