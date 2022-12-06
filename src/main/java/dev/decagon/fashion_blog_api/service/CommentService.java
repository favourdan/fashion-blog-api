package dev.decagon.fashion_blog_api.service;

import dev.decagon.fashion_blog_api.dto.CreateCommentDto;
import dev.decagon.fashion_blog_api.entities.Comment;

import java.util.List;

public interface CommentService {
    void createComment(Long post_id, CreateCommentDto commentDto);

    List<Comment> getAllPostComments(Long post_id);
}
