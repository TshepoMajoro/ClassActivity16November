package org.springtech.classactivity16november.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StudentRequests(
        @NotBlank(message = "First name is a required field.")
        @Size(max = 100, message = "First name should not exceed 100 characters.")
        String firstName,

        @NotBlank(message = "Last name is a required field.")
        @Size(max = 100, message = "Last name should not exceed 100 characters.")
        String lastName,

        @NotBlank(message = "Email is a required field.")
        @Email(message = "Enter a valid email address.")
        @Size(max = 255, message = "Email should not exceed 255 characters.")
        String email
) {
}
