package dev.decagon.fashion_blog_api.service.impl;

import dev.decagon.fashion_blog_api.dto.CreatePostDto;
import dev.decagon.fashion_blog_api.dto.UpdatePostDto;
import dev.decagon.fashion_blog_api.exceptions.NotFoundException;
import dev.decagon.fashion_blog_api.exceptions.UnauthorizedException;
import dev.decagon.fashion_blog_api.entities.Like;
import dev.decagon.fashion_blog_api.entities.Post;
import dev.decagon.fashion_blog_api.entities.User;
import dev.decagon.fashion_blog_api.repository.LikeRepository;
import dev.decagon.fashion_blog_api.repository.PostRepository;
import dev.decagon.fashion_blog_api.repository.UserRepository;
import dev.decagon.fashion_blog_api.service.PostService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Override
    public void createPost(CreatePostDto createPostDto) {
        Long userId = createPostDto.getUserId();
        if ( userId != 1L){
            throw new UnauthorizedException("Unauthorized User Action");
        }

        Post post = new Post();

        BeanUtils.copyProperties(createPostDto, post);

        postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPost(Long post_id) {

        return postRepository.findById(post_id)
                .orElseThrow(() -> {
                    throw new NotFoundException("Post with ID: " + post_id + " Not Found");
                });
    }

    @Override
    @Transactional
    public Post updatePost(Long post_id, UpdatePostDto updatePostDto) {
        String title = updatePostDto.getTitle();
        String description = updatePostDto.getDescription();

        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> {
                    throw new NotFoundException("Post with ID: " + post_id + " Not Found");
                });

        if(title != null && title.length() > 0 && !title.equals(post.getTitle())){
            post.setTitle(title);
            post.setUpdatedAt(LocalDateTime.now());
        }

        if(description != null && description.length() > 0 && !description.equals(post.getDescription())){
            post.setDescription(description);
            post.setUpdatedAt(LocalDateTime.now());
        }

        return post;
    }

    @Override
    public void deletePost(Long post_id) {
        boolean exists = postRepository.existsById(post_id);

        if(!exists){
            throw new NotFoundException("Post with ID: " + post_id + " Not Found");
        }

        postRepository.deleteById(post_id);
    }

    @Override
    public String likePost(Long post_id, Long user_id) {

        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> {
                    throw new NotFoundException("Post with ID: " + post_id + " Not Found");
                });

        User user = userRepository.findById(user_id)
                .orElseThrow(() -> {
                    throw new NotFoundException("User with ID: " + user_id + " Not Found");
                });

        Optional<Like> liked = likeRepository.findByPostAndUser(post, user);

        if (liked.isPresent()) {
            likeRepository.delete(liked.get());
            return "User Unliked Post";
        }

        Like like = new Like();
        like.setPost(post);
        like.setUser(user);

        likeRepository.save(like);

        return "User liked Post";
    }
}
