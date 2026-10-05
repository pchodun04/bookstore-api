package pjatk.tpo.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pjatk.tpo.demo.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
