package dev.decagon.fashion_blog_api.repository;

import dev.decagon.fashion_blog_api.entities.Like;
import dev.decagon.fashion_blog_api.entities.Post;
import dev.decagon.fashion_blog_api.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LikeRepositoryTest {

    @Autowired
    private LikeRepository underTest;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    void findByPostAndUser() {
        User user = new User("Anna", "anna@gmail.com", "1234");
        user = userRepository.save(user);

        Post post = new Post("Test Title", "Test Description");
        post = postRepository.save(post);

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        underTest.save(like);

        Like actual = underTest.findByPostAndUser(post, user).orElseThrow();

        assertThat(actual).isEqualTo(like);
    }
}
