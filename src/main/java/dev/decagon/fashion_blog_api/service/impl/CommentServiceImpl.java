package dev.decagon.fashion_blog_api.service.impl;

import dev.decagon.fashion_blog_api.dto.CreateCommentDto;
import dev.decagon.fashion_blog_api.exceptions.NotFoundException;
import dev.decagon.fashion_blog_api.entities.Comment;
import dev.decagon.fashion_blog_api.entities.Post;
import dev.decagon.fashion_blog_api.entities.User;
import dev.decagon.fashion_blog_api.repository.CommentRepository;
import dev.decagon.fashion_blog_api.repository.PostRepository;
import dev.decagon.fashion_blog_api.repository.UserRepository;
import dev.decagon.fashion_blog_api.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public void createComment(Long post_id, CreateCommentDto commentDto) {

        String body = commentDto.getMessage();
        Long userId = commentDto.getUserId();

        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> {
                    throw new NotFoundException("Post with ID: " + post_id + " Not Found");
                });

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new NotFoundException("User with ID: " + userId + " Not Found");
                });

        Comment comment = new Comment();
        comment.setBody(body);
        comment.setPost(post);
        comment.setUser(user);

        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllPostComments(Long post_id) {
        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> {
                    throw new NotFoundException("Post with ID: " + post_id + " Not Found");
                });

        return commentRepository.findAllByPost(post);
    }
}
