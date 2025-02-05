package com.jpbportal.dto;

import com.jpbportal.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    @NotBlank(message = "must be a character")
    private String name;
    @NotBlank(message = "must be a email")
    @Email(message = "Email is Invalid!")
    private String email;
    @NotBlank(message = "must be a password")
    private String password;
    private AccountType accountType;
    private Long profileId;


    public User toEntity() {
        return new User( this.id, this.name, this.email, this.password, this.accountType, this.profileId);
    }
}
