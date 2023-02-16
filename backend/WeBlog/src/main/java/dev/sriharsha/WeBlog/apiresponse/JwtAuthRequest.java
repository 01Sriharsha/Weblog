package dev.sriharsha.WeBlog.apiresponse;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthRequest {

    @NotEmpty(message = "Email cannot be empty")
    @Email(regexp = "[A-Za-z0-9\\.\\_]+[@][a-zA-Z]{3,10}[\\.][a-zA-Z]{2,3}$", message = "Enter valid email")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
//    @Pattern(regexp = "[A-Za-z\\.\\s]+[@$#]+[a-zA-Z0-9]+$", message = "Password must contain 1 Uppercase, 1 special character and a combination of digits and characters ")
    private String password;
}
