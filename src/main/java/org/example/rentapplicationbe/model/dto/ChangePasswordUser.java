package org.example.rentapplicationbe.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordUser {
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 8, message = "Password length is 6-8 characters")
    private String currentPassword;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 8, message = "Password length is 6-8 characters")
    private String newPassword;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 8, message = "Password length is 6-8 characters")
    private String checkNewPassword;
}
