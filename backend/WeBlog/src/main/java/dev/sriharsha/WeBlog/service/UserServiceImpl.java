package dev.sriharsha.WeBlog.service;

import dev.sriharsha.WeBlog.constant.AppConstant;
import dev.sriharsha.WeBlog.dto.UserDto;
import dev.sriharsha.WeBlog.entity.Role;
import dev.sriharsha.WeBlog.entity.User;
import dev.sriharsha.WeBlog.exception.ApiException;
import dev.sriharsha.WeBlog.exception.ResourceNotFoundException;
import dev.sriharsha.WeBlog.repository.RoleRepository;
import dev.sriharsha.WeBlog.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private HttpServletResponse response;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        try {
            User user = this.dtoToUser(userDto);
            //encode the password
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            //set role (By default every user is a NORMAL_USER)
            Role role = roleRepository.findById(AppConstant.NORMAL_USER)
                    .orElseThrow(() -> new ResourceNotFoundException("Role Not Found"));
            user.getRoles().add(role);
            User savedUser = userRepository.save(user);
            return this.userToDto(savedUser);
        } catch (IllegalArgumentException exception) {
            response.setStatus(400);
            throw new ApiException("Server Error! User Not Created");
        }
    }

    @Override
    public UserDto userDetailByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Email", "email", username));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = userRepository.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(Integer userId, UserDto userDto) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        var updatedUser = userRepository.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepository.delete(user);
    }

    @Override
    public UserDto getUser(Integer userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> usersDto = users.stream()
                .map(this::userToDto) //(or) user -> this.userToDto(user)
                .collect(Collectors.toList());
        return usersDto;
    }

    private User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        return user;
//        user.setId(userDto.getId());
//        user.setUsername(userDto.getUsername());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
//        return user;
    }

    private UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
        /* userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAbout(user.getAbout());
        return userDto; */
    }
}
