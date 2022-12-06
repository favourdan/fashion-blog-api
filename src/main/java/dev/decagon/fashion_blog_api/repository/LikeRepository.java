package dev.decagon.fashion_blog_api.repository;

import dev.decagon.fashion_blog_api.entities.Like;
import dev.decagon.fashion_blog_api.entities.Post;
import dev.decagon.fashion_blog_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostAndUser(Post post, User user);

}
