package br.com.fiap.OutfitHub.dto;

import br.com.fiap.OutfitHub.models.User;
import br.com.fiap.OutfitHub.models.enums.Role;

public record UserResponse(
        Long id,
        String name,
        String email,
        Role role,
        String token
) {
    public static UserResponse fromEntity(User user, String token) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                token
        );
    }
}
