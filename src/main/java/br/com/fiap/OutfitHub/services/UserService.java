package br.com.fiap.OutfitHub.services;

import br.com.fiap.OutfitHub.models.User;
import br.com.fiap.OutfitHub.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public User auth(String email, String password) {

        Optional<User> userOpt = repository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Credenciais inválidas.");
        }

        User user = userOpt.get();

        // Verifica se a password enviada coincide com o hash do banco
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas.");
        }

        return user;
    }

}
