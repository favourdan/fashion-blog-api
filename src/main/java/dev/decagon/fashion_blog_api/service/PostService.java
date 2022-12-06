package dev.decagon.fashion_blog_api.service;

import dev.decagon.fashion_blog_api.dto.CreatePostDto;
import dev.decagon.fashion_blog_api.dto.UpdatePostDto;
import dev.decagon.fashion_blog_api.entities.Post;

import java.util.List;

public interface PostService {
    void createPost(CreatePostDto createPostDto);

    List<Post> getAllPosts();

    Post getPost(Long post_id);

    Post updatePost(Long post_id, UpdatePostDto updatePostDto);

    void deletePost(Long post_id);

    String likePost(Long post_id, Long user_id);
}
