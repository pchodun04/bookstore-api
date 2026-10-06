package bookstore.dto;

import bookstore.model.CartItem;

public class dto {
    public record UserRequest(
            String name,
            String email,
            String password
    ){}

    public record UserResponse(
            Integer id,
            String name,
            String email
    ){}

    public record UserUpdateRequest(
            String name
    ){}

    public record LoginRequest(
            String email,
            String password
    ){}

    public record ChangePasswordRequest(
            String oldPassword,
            String newPassword
    ){}

    public record Book(
            String title,
            String author,
            int pages,
            double price
    ){}

    public record CartItemResponse(
            Integer id,
            String bookTitle,
            double bookPrice,
            int quantity,
            double totalPrice
    ){
        public static CartItemResponse from(CartItem item) {
            return new CartItemResponse(
                    item.getId(),
                    item.getBook().getTitle(),
                    item.getBook().getPrice(),
                    item.getQuantity(),
                    item.getBook().getPrice() * item.getQuantity()
            );
        }
    }
}
