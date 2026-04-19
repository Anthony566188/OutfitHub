package br.com.fiap.OutfitHub.security;

import br.com.fiap.OutfitHub.models.User;
import br.com.fiap.OutfitHub.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Tenta recuperar o token do cabeçalho da requisição
        var token = this.recoverToken(request);

        if (token != null) {
            // Se o token existir, valida-o e recupera o e-mail do utilizador
            var email = tokenService.validateToken(token);

            if (!email.isEmpty()) {
                // Procura o utilizador na base de dados
                var userOpt = userRepository.findByEmail(email);

                if (userOpt.isPresent()) {
                    User user = userOpt.get();

                    // Cria o objeto de autenticação com as roles do utilizador
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                    // Guarda o utilizador autenticado no contexto do Spring Security
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        // Continua o fluxo da requisição (seja para o Controller ou para devolver um erro de acesso negado)
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;

        // O padrão de mercado é enviar o token com o prefixo "Bearer ", por isso removemos essa palavra
        return authHeader.replace("Bearer ", "");
    }
}