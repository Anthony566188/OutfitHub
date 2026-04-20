package br.com.fiap.OutfitHub.repositories;

import br.com.fiap.OutfitHub.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RepositoryRestResource(exported = false) // Desativ a exposição automática do Data REST
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
