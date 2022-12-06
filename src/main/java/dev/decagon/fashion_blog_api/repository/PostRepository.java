package dev.decagon.fashion_blog_api.repository;

import dev.decagon.fashion_blog_api.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}