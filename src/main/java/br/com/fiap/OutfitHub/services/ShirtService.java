package br.com.fiap.OutfitHub.services;

import br.com.fiap.OutfitHub.models.Shirt;
import br.com.fiap.OutfitHub.repositories.ShirtRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ShirtService {

    private final ShirtRepository repository;
    private final CloudinaryService cloudinaryService;

    public ShirtService(ShirtRepository repository, CloudinaryService cloudinaryService) {
        this.repository = repository;
        this.cloudinaryService = cloudinaryService;
    }

    public List<Shirt> listAll() {
        return repository.findAll();
    }

    public Shirt saveShirt(Shirt shirt, MultipartFile image) throws IOException {
        // Faz o upload da imagem e recebe a URL do Cloudinary
        String imageUrl = cloudinaryService.upload(image);

        shirt.setImagePath(imageUrl);
        return repository.save(shirt);
    }

    public void deleteShirt(Long id) {

        var optionalShirt = getShirtById(id);
        if (optionalShirt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Camisa não encontrada");
        }

        repository.deleteById(id);
    }

    public Optional<Shirt> getShirtById(Long id) {
        return repository.findById(id);
    }
}
