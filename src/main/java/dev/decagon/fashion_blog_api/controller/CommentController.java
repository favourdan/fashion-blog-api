package dev.decagon.fashion_blog_api.controller;

import dev.decagon.fashion_blog_api.dto.CreateCommentDto;
import dev.decagon.fashion_blog_api.entities.Comment;
import dev.decagon.fashion_blog_api.service.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/posts/{post_id}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<String> createComment(@PathVariable Long post_id,
                                                @Valid @RequestBody CreateCommentDto commentDto){

        commentService.createComment(post_id, commentDto);

        return new ResponseEntity<>("User added a comment.", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllPostComments(@PathVariable Long post_id){

        List<Comment> comments = commentService.getAllPostComments(post_id);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
