package br.com.fiap.OutfitHub.security;

import br.com.fiap.OutfitHub.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Lê o "segredo" do application.properties. Se não existir, usa um valor padrão (apenas para desenvolvimento)
    @Value("${api.security.token.secret:meu-segredo-super-secreto}")
    private String secret;

    public String generateToken(User user) {
        try {
            // O algoritmo de encriptação
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("OutfitHub-API") // Quem está a emitir o token
                    .withSubject(user.getEmail()) // A quem pertence o token (usamos o email)
                    .withExpiresAt(getExpirationDate()) // Quando o token perde a validade
                    .sign(algorithm); // Assina e gera a string final
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o token JWT", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("OutfitHub-API")
                    .build()
                    .verify(token)
                    .getSubject(); // Devolve o email se o token for válido
        } catch (JWTVerificationException exception) {
            // Retorna vazio se o token for inválido, expirado ou adulterado
            return "";
        }
    }

    private Instant getExpirationDate() {
        // O token expira em 2 horas.
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}