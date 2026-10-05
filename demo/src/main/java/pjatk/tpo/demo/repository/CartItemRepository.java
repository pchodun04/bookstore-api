package pjatk.tpo.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pjatk.tpo.demo.model.CartItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findCartItemsByUserId(Integer id);
    Optional<CartItem> findCartItemByUserIdAndBookId(Integer userId, Integer bookId);
}
