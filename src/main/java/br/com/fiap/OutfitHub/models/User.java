package br.com.fiap.OutfitHub.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DB_OUTFITHUB_USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    private String username;
    private String email;
    @Column(name = "USER_PASSWORD")
    private String password;
    @Column(name = "USER_ROLE")
    private String role;

}
