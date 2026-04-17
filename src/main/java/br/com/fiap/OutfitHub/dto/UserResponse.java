package br.com.fiap.OutfitHub.dto;

import br.com.fiap.OutfitHub.models.User;
import br.com.fiap.OutfitHub.models.enums.Role;

public record UserResponse(
        Long id,
        String username,
        String email,
        Role role
) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}
