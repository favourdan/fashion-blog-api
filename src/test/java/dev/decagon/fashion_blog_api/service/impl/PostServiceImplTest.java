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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private LikeRepository likeRepository;

    private PostService underTest;

    @BeforeEach
    void setUp() {
        underTest = new PostServiceImpl(postRepository, userRepository, likeRepository);
    }

    @Test
    void testCreatePost() {
        //given
        CreatePostDto postDto = new CreatePostDto("Title", "Description for testing", 1L);
        Post post = new Post("Title", "Description for testing");

        //when
        underTest.createPost(postDto);

        //then
        ArgumentCaptor<Post> studentArgumentCaptor = ArgumentCaptor.forClass(Post.class);

        verify(postRepository).save(studentArgumentCaptor.capture());

        Post capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(post);
    }

    @Test
    void testCreatePostUnauthorizedUser() {
        //given
        CreatePostDto postDto = new CreatePostDto("Title", "Description for testing", 2L);

        //when
        assertThatThrownBy(() -> underTest.createPost(postDto))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("Unauthorized User Action");

        //then
        verify(postRepository, never()).save(any());
    }

    @Test
    void getAllPosts() {

        underTest.getAllPosts();

        verify(postRepository).findAll();
    }

    @Test
    void getPost() {
        Post post = new Post("Title", "Description for testing");
        post.setId(1L);

        given(postRepository.findById(post.getId()))
                .willReturn(Optional.of(post));

        underTest.getPost(post.getId());

        verify(postRepository).findById(any());
    }

    @Test
    void getPostNotFound() {
        Post post = new Post("Title", "Description for testing");
        post.setId(100L);

        given(postRepository.findById(post.getId()))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getPost(post.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Post with ID: " + post.getId() + " Not Found");
    }

    @Test
    void updatePost() {
        UpdatePostDto postDto = new UpdatePostDto("Updated Title", "Description for testing", 1L);
        Post post = new Post("Updated Title", "Description for testing");
        post.setId(1L);

        given(postRepository.findById(post.getId()))
                .willReturn(Optional.of(post));

        Post actual = underTest.updatePost(post.getId(), postDto);

        assertThat(actual).isEqualTo(post);
    }

    @Test
    void testDeletePost() {
        Long post_id = 1L;

        given(postRepository.existsById(post_id)).willReturn(true);
        underTest.deletePost(post_id);

        verify(postRepository).deleteById(post_id);
    }

    @Test
    void testDeletePostNotFound() {
        Long post_id = 100L;

        given(postRepository.existsById(post_id)).willReturn(false);

        assertThatThrownBy(() -> underTest.deletePost(post_id))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Post with ID: " + post_id + " Not Found");

        verify(postRepository, never()).deleteById(post_id);
    }

    @Test
    void testLikePost() {
        Post post = new Post("Title", "Description for testing");
        post.setId(1L);

        User user = new User("Tom", "tom@email.com", "1234");
        user.setId(2L);

        given(postRepository.findById(post.getId())).willReturn(Optional.of(post));
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));


        underTest.likePost(post.getId(), user.getId());

        verify(likeRepository).save(any());
    }

    @Test
    void testUnlikePost() {
        Post post = new Post("Title", "Description for testing");
        post.setId(1L);

        User user = new User("Tom", "tom@email.com", "1234");
        user.setId(2L);

        given(postRepository.findById(post.getId())).willReturn(Optional.of(post));
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        Like like = new Like();
        like.setPost(post);
        like.setUser(user);

        given(likeRepository.findByPostAndUser(post, user)).willReturn(Optional.of(like));

        underTest.likePost(post.getId(), user.getId());

        verify(likeRepository).delete(any());
    }
}