package dev.decagon.fashion_blog_api.service;

import dev.decagon.fashion_blog_api.dto.AddUserDto;
import dev.decagon.fashion_blog_api.dto.LoginUserDto;

public interface UserService {
    void addUser(AddUserDto addUserDto);

    void loginUser(LoginUserDto loginUserDto);
}