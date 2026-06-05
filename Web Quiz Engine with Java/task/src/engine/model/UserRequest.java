package engine.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        @Pattern(
                regexp = "^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$",
                message = "Email must contain @ and a domain extension"
        )
        String email,


        @NotBlank(message = "Password cannot be blank")
        @Size(min = 5, message = "Password must be at least 5 characters long")
        String password
) {
}
