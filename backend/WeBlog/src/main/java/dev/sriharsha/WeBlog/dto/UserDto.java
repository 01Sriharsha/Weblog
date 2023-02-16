package dev.sriharsha.WeBlog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.sriharsha.WeBlog.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private int id;
    @NotEmpty(message = "username cannot be empty")
    @Size(min = 4, max = 20, message = "username should be least of 4 characters" +
            " & " + "should not exceed more than 20 characters")
    private String username;
    @Email(regexp = "[A-Za-z0-9\\.\\_]+[@][a-zA-Z]{3,10}[\\.][a-zA-Z]{2,3}$", message = "Enter valid email")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    /* JsonProperty - Do not show password in Json data but will give write access to it
    Best Compared ro JSONIgnore where it doesn't give access to both read and write */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "Password cannot be empty")
    @Pattern(regexp = "[A-Za-z\\.\\s]+[@$#]+[a-zA-Z0-9]+$", message = "Password must contain 1 Uppercase, 1 special character and a combination of digits and characters ")
    @Size(min = 6, max = 20, message = "password should have minimum of 6 characters" +
            " & " + "should not not exceed more than 20 characters")
    private String password;
    @NotEmpty(message = "About cannot be empty")
    @Size(min = 10, max = 300, message = "About should be least of 10 characters")
    private String about;

    private Set<Role> roles = new HashSet<>();
}
