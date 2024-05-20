package com.myprojects.user_management_system_dto_validations.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class UserDto {

    private Long userId;

    @NotEmpty(message = "User name should not be null or empty")
    private String userName;

    @NotEmpty(message = "User email should not be null or empty")
    @Email(message = "enter valid user name")
    private String userEmail;

    @NotEmpty(message = "User age should not be null or empty")
    private int userAge;
}
