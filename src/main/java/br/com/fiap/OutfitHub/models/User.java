package br.com.fiap.OutfitHub.models;

import br.com.fiap.OutfitHub.models.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "DB_OUTFITHUB_USERS")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "USERNAME", nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "USER_PASSWORD", nullable = false)
    private String password;
    @Column(name = "USER_ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    // --- MÉTODOS OBRIGATÓRIOS DA INTERFACE USERDETAILS ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // O Spring Security espera o prefixo "ROLE_" para validar o hasRole("ADMIN")
        if (this.role == Role.ADMIN) {
            // Se for ADMIN, recebe as permissões de ADMIN e também de USER
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            // Se for apenas USER, recebe apenas a permissão base
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Retornar 'true' significa que a conta não expira
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Retornar 'true' significa que a conta não está bloqueada
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Retornar 'true' significa que a palavra-passe não expira
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Retornar 'true' significa que o utilizador está ativo
        return true;
    }

}
