package dev.decagon.fashion_blog_api.controller;

import dev.decagon.fashion_blog_api.dto.CreatePostDto;
import dev.decagon.fashion_blog_api.dto.UpdatePostDto;
import dev.decagon.fashion_blog_api.entities.Post;
import dev.decagon.fashion_blog_api.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@Valid @RequestBody CreatePostDto createPostDto){

        postService.createPost(createPostDto);

        return new ResponseEntity<>("Post Created Successfully", HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> posts = postService.getAllPosts();

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(path = "{post_id}")
    public ResponseEntity<Post> getPost(@PathVariable Long post_id){
        Post post = postService.getPost(post_id);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping(path = "{post_id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long post_id, @Valid @RequestBody UpdatePostDto updatePostDto){
        Post post = postService.updatePost(post_id, updatePostDto);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping(path = "{post_id}")
    public ResponseEntity<String> deletePost(@PathVariable Long post_id){

        postService.deletePost(post_id);

        return new ResponseEntity<>("Post Deleted", HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "{post_id}/like/{user_id}")
    public ResponseEntity<String> likePost(@PathVariable Long post_id, @PathVariable Long user_id){

        String msg = postService.likePost(post_id, user_id);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
