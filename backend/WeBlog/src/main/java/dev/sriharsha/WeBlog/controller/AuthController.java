package dev.sriharsha.WeBlog.controller;

import dev.sriharsha.WeBlog.apiresponse.JwtAuthRequest;
import dev.sriharsha.WeBlog.apiresponse.JwtAuthResponse;
import dev.sriharsha.WeBlog.dto.UserDto;
import dev.sriharsha.WeBlog.exception.ApiException;
import dev.sriharsha.WeBlog.security.JwtTokenHelper;
import dev.sriharsha.WeBlog.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody @Valid JwtAuthRequest request) {

        Authentication authenticate = null;

        try {
            authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    ));
            authenticate.getAuthorities().forEach(e -> System.out.println(e));
        } catch (BadCredentialsException exception) {
            throw new ApiException(exception.getMessage() + "! Invalid Username Or Password");
        }

        String token = this.jwtTokenHelper.generateToken(authenticate);
        //sending user details with token
        UserDto userDto = userService.userDetailByUsername(authenticate.getName());
        return new ResponseEntity<>(new JwtAuthResponse(token, userDto), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerTheUser(@RequestBody @Valid UserDto userDto) throws SQLIntegrityConstraintViolationException {
        UserDto response = userService.registerNewUser(userDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
