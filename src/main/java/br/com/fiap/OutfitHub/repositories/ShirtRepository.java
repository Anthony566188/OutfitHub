package br.com.fiap.OutfitHub.repositories;

import br.com.fiap.OutfitHub.models.Shirt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShirtRepository extends JpaRepository<Shirt, Long> {
}
