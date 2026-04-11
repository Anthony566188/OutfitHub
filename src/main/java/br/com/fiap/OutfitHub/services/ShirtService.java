package br.com.fiap.OutfitHub.services;

import br.com.fiap.OutfitHub.models.Shirt;
import br.com.fiap.OutfitHub.repositories.ShirtRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShirtService {

    private final ShirtRepository repository;

    public ShirtService(ShirtRepository repository) {
        this.repository = repository;
    }

    public List<Shirt> listShirts(){
        return repository.findAll();
    }
}
