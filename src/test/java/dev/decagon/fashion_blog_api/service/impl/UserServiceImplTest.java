package dev.decagon.fashion_blog_api.service.impl;

import dev.decagon.fashion_blog_api.dto.AddUserDto;
import dev.decagon.fashion_blog_api.dto.LoginUserDto;
import dev.decagon.fashion_blog_api.exceptions.AlreadyExistException;
import dev.decagon.fashion_blog_api.exceptions.BadRequestException;
import dev.decagon.fashion_blog_api.exceptions.NotFoundException;
import dev.decagon.fashion_blog_api.entities.User;
import dev.decagon.fashion_blog_api.repository.UserRepository;
import dev.decagon.fashion_blog_api.service.UserService;
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
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserServiceImpl(userRepository);
    }

    @Test
    void testAddUser() {
        //given
        AddUserDto userDto = new AddUserDto("Adrian", "adrian@gmail.com", "1234");
        User user = new User("Adrian", "adrian@gmail.com", "1234");
        //when
        underTest.addUser(userDto);

        //then
        ArgumentCaptor<User> studentArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(studentArgumentCaptor.capture());

        User capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(user);
    }

    @Test
    void testAddStudentWhenEmailTaken() {
        //given
        AddUserDto userDto = new AddUserDto("Adrian", "adrian@gmail.com", "1234");

        given(userRepository.existsByEmail(userDto.getEmail()))
                .willReturn(true);

        //when
        //then
        assertThatThrownBy(() -> underTest.addUser(userDto))
                .isInstanceOf(AlreadyExistException.class)
                .hasMessage("Email Already Exist");

        verify(userRepository, never()).save(any());

    }

    @Test
    void testLoginUser() {
        //given
        LoginUserDto userDto = new LoginUserDto();
        userDto.setEmail("adrian@gmail.com");
        userDto.setPassword("1234");

        User user = new User("Adrian", "adrian@gmail.com", "1234");
        given(userRepository.findUserByEmail(userDto.getEmail())).willReturn(Optional.of(user));

        //when
        underTest.loginUser(userDto);

        //then
        verify(userRepository).findUserByEmail(userDto.getEmail());
    }

    @Test
    void testLoginUserNotFound() {
        //given
        LoginUserDto userDto = new LoginUserDto();
        userDto.setEmail("adrian@gmail.com");
        userDto.setPassword("1234");

        //when
        //then
        assertThatThrownBy(() -> underTest.loginUser(userDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with email: " + userDto.getEmail() + " Not Found");
    }

    @Test
    void testLoginUserPasswordIncorrect() {
        //given
        LoginUserDto userDto = new LoginUserDto();
        userDto.setEmail("adrian@gmail.com");
        userDto.setPassword("12345");

        User user = new User("Adrian", "adrian@gmail.com", "1234");
        given(userRepository.findUserByEmail(userDto.getEmail())).willReturn(Optional.of(user));

        //when
        //then
        assertThatThrownBy(() -> underTest.loginUser(userDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Incorrect Password");
    }
}