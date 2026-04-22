package br.com.fiap.OutfitHub.services;

import br.com.fiap.OutfitHub.models.Shirt;
import br.com.fiap.OutfitHub.models.enums.Status;
import br.com.fiap.OutfitHub.repositories.ShirtRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

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
        shirt.setStatus(Status.ESTOQUE);
        return repository.save(shirt);
    }

    public void deleteShirt(Long id) {

        findShirtById(id);

        repository.deleteById(id);
    }

    public Shirt updateShirt(Shirt shirt, Long id, MultipartFile image) throws IOException {

//        if (studyPlan.getName() == null || studyPlan.getName().isBlank()) {
//            throw new BusinessException("Nome do plano de estudo é obrigatório");
//        }

        // Busca a camisa existente no banco
        Shirt existingShirt = findShirtById(id);

        // Verifica se uma nova imagem foi enviada na requisição
        if (image != null && !image.isEmpty()) {
            // Faz o upload da nova imagem para o Cloudinary
            String newImageUrl = cloudinaryService.upload(image);
            // Atualiza o caminho com a nova URL gerada
            existingShirt.setImagePath(newImageUrl);

        }

        existingShirt.setName(shirt.getName());
        existingShirt.setSize(shirt.getSize());
        existingShirt.setStatus(shirt.getStatus());
        existingShirt.setPrice(shirt.getPrice());

        // Salva a alteração
        return repository.save(existingShirt);

    }

    private Shirt findShirtById(Long id){
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Camisa de id " + id + " não encontrada"));
    }

}
