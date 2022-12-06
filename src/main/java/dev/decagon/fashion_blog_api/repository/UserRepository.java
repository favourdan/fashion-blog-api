package dev.decagon.fashion_blog_api.repository;

import dev.decagon.fashion_blog_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String userEmail);

    Optional<User> findUserByEmail(String userEmail);
}
