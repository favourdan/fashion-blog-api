package dev.decagon.fashion_blog_api.repository;

import dev.decagon.fashion_blog_api.entities.Comment;
import dev.decagon.fashion_blog_api.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
}
