package br.com.fiap.OutfitHub.dto;

import br.com.fiap.OutfitHub.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record UserRequest(
    String username,
    @Email
    @NotBlank(message = "O email é obrigatório")
    String email,
    @NotBlank(message = "A senha é obrigatória")
    String password
) {

    public User toEntity() {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }

}
