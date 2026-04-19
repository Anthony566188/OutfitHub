package br.com.fiap.OutfitHub.controllers;

import br.com.fiap.OutfitHub.dto.UserRequest;
import br.com.fiap.OutfitHub.dto.UserResponse;
import br.com.fiap.OutfitHub.models.User;
import br.com.fiap.OutfitHub.security.TokenService;
import br.com.fiap.OutfitHub.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService service;

    @Autowired
    private TokenService tokenService;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/auth")
    public UserResponse auth(@RequestBody @Valid UserRequest userRequest) {

        log.info("Autenticando utilizador: {}", userRequest.email());

        // Valida as credenciais no banco de dados
        User user = service.auth(userRequest.email(), userRequest.password());

        // Gera o Token JWT para este utilizador
        String token = tokenService.generateToken(user);

        // Retorna os dados do utilizador + o Token
        return UserResponse.fromEntity(user, token);

    }

}
