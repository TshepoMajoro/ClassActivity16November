package org.springtech.classactivity16november.DTO;

public record StudentResponse(
        Long id,
        String firstName,
        String lastName,
        String email
) {
}
