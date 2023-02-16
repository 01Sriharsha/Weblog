package dev.sriharsha.WeBlog.service;

import dev.sriharsha.WeBlog.dto.UserDto;

import java.util.List;

public interface UserService {

    public UserDto registerNewUser(UserDto userDto);

    public UserDto userDetailByUsername(String username);

    public UserDto createUser(UserDto user);

    public UserDto updateUser(Integer userId, UserDto user);

    public void deleteUser(Integer userId);

    public UserDto getUser(Integer userId);

    public List<UserDto> getAllUsers();
}
